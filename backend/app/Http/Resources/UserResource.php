<?php

namespace App\Http\Resources;

use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

final class UserResource extends JsonResource
{
    public function toArray(Request $request): array { return ['id' => $this->id, 'username' => $this->username, 'name' => $this->name, 'email' => $this->when($request->user()?->is($this->resource), $this->email), 'bio' => $this->bio, 'created_at' => $this->created_at?->toISOString()]; }
}
