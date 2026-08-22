<?php

namespace App\Http\Controllers\Api\V1;

use App\Http\Requests\LoginRequest;
use App\Http\Requests\RegisterRequest;
use App\Http\Resources\UserResource;
use App\Models\User;
use App\Services\TokenService;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Facades\Hash;

final class AuthController
{
    public function register(RegisterRequest $request, TokenService $tokens): JsonResponse { $data = $request->validated(); $user = User::query()->create(['username' => strtolower($data['username']), 'name' => $data['name'], 'email' => strtolower($data['email']), 'password' => $data['password']]); return response()->json(['user' => new UserResource($user), 'token' => $tokens->issue($user, $data['device_name'] ?? null)], 201); }
    public function login(LoginRequest $request, TokenService $tokens): JsonResponse { $data = $request->validated(); $user = User::query()->where('email', strtolower($data['email']))->first(); if (!$user || !Hash::check($data['password'], $user->password)) return response()->json(['message' => 'The provided credentials are incorrect.'], 422); return response()->json(['user' => new UserResource($user), 'token' => $tokens->issue($user, $data['device_name'] ?? null)]); }
    public function logout(\Illuminate\Http\Request $request): JsonResponse { $request->attributes->get('apiToken')?->delete(); return response()->json(status: 204); }
}
