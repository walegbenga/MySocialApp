<?php

namespace Tests\Feature\Api\V1;

use Illuminate\Foundation\Testing\RefreshDatabase;
use Tests\TestCase;

final class AuthenticationTest extends TestCase
{
    use RefreshDatabase;

    private array $registration = ['username' => 'ada_lovelace', 'name' => 'Ada Lovelace', 'email' => 'ada@example.test', 'password' => 'SecurePass1', 'password_confirmation' => 'SecurePass1'];

    public function test_a_user_can_register_and_get_their_profile(): void
    {
        $response = $this->postJson('/api/v1/auth/register', $this->registration)->assertCreated()->assertJsonPath('user.username', 'ada_lovelace');
        $token = $response->json('token');
        $this->assertIsString($token);
        $this->getJson('/api/v1/me', ['Authorization' => "Bearer {$token}"])->assertOk()->assertJsonPath('data.email', 'ada@example.test');
        $this->assertDatabaseMissing('api_tokens', ['token_hash' => $token]);
    }

    public function test_registration_requires_unique_email_and_username(): void
    {
        $this->postJson('/api/v1/auth/register', $this->registration)->assertCreated();
        $this->postJson('/api/v1/auth/register', $this->registration)->assertUnprocessable()->assertJsonValidationErrors(['username', 'email']);
    }

    public function test_login_logout_and_profile_update_require_valid_authentication(): void
    {
        $this->postJson('/api/v1/auth/register', $this->registration)->assertCreated();
        $login = $this->postJson('/api/v1/auth/login', ['email' => 'ada@example.test', 'password' => 'SecurePass1'])->assertOk();
        $token = $login->json('token');
        $this->patchJson('/api/v1/me', ['bio' => 'First programmer'], ['Authorization' => "Bearer {$token}"])->assertOk()->assertJsonPath('data.bio', 'First programmer');
        $this->postJson('/api/v1/auth/logout', [], ['Authorization' => "Bearer {$token}"])->assertNoContent();
        $this->getJson('/api/v1/me', ['Authorization' => "Bearer {$token}"])->assertUnauthorized();
    }

    public function test_public_profile_does_not_expose_email(): void
    {
        $this->postJson('/api/v1/auth/register', $this->registration)->assertCreated();
        $this->getJson('/api/v1/users/ada_lovelace')->assertOk()->assertJsonMissing(['email' => 'ada@example.test']);
    }
}
