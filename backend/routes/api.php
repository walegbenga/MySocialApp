<?php
use App\Http\Controllers\Api\V1\HealthController;
use App\Http\Controllers\Api\V1\AuthController;
use App\Http\Controllers\Api\V1\UserController;
use Illuminate\Support\Facades\Route;
Route::prefix('v1')->group(function (): void {
    Route::get('/health', HealthController::class)->name('api.v1.health');
    Route::middleware('throttle:auth')->group(function (): void { Route::post('/auth/register', [AuthController::class, 'register']); Route::post('/auth/login', [AuthController::class, 'login']); });
    Route::get('/users/{user:username}', [UserController::class, 'show']);
    Route::middleware('api.token')->group(function (): void { Route::post('/auth/logout', [AuthController::class, 'logout']); Route::get('/me', [UserController::class, 'me']); Route::patch('/me', [UserController::class, 'update']); });
});
