<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;
use Illuminate\Validation\Rules\Password;

final class RegisterRequest extends FormRequest
{
    public function authorize(): bool { return true; }
    public function rules(): array { return ['username' => ['required', 'string', 'min:3', 'max:30', 'regex:/^[a-zA-Z0-9_]+$/', 'unique:users,username'], 'name' => ['required', 'string', 'min:2', 'max:80'], 'email' => ['required', 'email:rfc', 'max:255', 'unique:users,email'], 'password' => ['required', 'confirmed', Password::min(8)->mixedCase()->numbers()], 'device_name' => ['nullable', 'string', 'max:100']]; }
}
