<?php

namespace App\Http\Controllers\Api\V1;

use App\Http\Requests\UpdateProfileRequest;
use App\Http\Resources\UserResource;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Gate;

final class UserController
{
    public function me(Request $request): UserResource { return new UserResource($request->user()); }
    public function show(User $user): UserResource { return new UserResource($user); }
    public function update(UpdateProfileRequest $request): UserResource { $user = $request->user(); Gate::authorize('update', $user); $data = $request->validated(); foreach (['username', 'email'] as $field) if (isset($data[$field])) $data[$field] = strtolower($data[$field]); $user->update($data); return new UserResource($user->refresh()); }
}
