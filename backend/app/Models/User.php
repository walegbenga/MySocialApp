<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Notifications\Notifiable;
use Illuminate\Database\Eloquent\Relations\HasMany;

final class User extends Authenticatable
{
    use HasFactory, Notifiable;
    protected $fillable = ['username', 'name', 'email', 'password', 'bio'];
    protected $hidden = ['password'];
    protected function casts(): array { return ['password' => 'hashed']; }
    public function apiTokens(): HasMany { return $this->hasMany(ApiToken::class); }
}
