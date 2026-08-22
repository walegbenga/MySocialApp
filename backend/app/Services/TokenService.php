<?php

namespace App\Services;

use App\Models\User;
use Illuminate\Support\Str;

final class TokenService
{
    public function issue(User $user, ?string $deviceName = null): string
    {
        $plainTextToken = Str::random(80);
        $user->apiTokens()->create(['token_hash' => hash('sha256', $plainTextToken), 'device_name' => $deviceName, 'expires_at' => now()->addDays(90)]);
        return $plainTextToken;
    }
}
