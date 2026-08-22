<?php
namespace Tests\Feature\Api\V1;
use Tests\TestCase;
final class HealthTest extends TestCase { public function test_health_endpoint_returns_healthy_json(): void { $this->getJson('/api/v1/health')->assertOk()->assertExactJson(['status' => 'healthy']); } }
