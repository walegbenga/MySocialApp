<?php

namespace App\Policies;

use App\Models\User;

final class UserPolicy { public function update(User $actor, User $user): bool { return $actor->is($user); } }
