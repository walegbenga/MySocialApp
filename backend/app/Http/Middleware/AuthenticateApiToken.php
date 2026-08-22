<?php

namespace App\Http\Middleware;

use App\Models\ApiToken;
use Closure;
use Illuminate\Http\Request;
use Symfony\Component\HttpFoundation\Response;

final class AuthenticateApiToken
{
    public function handle(Request $request, Closure $next): Response
    {
        $token = $request->bearerToken();
        if (!$token) return response()->json(['message' => 'Unauthenticated.'], 401);
        $record = ApiToken::query()->with('user')->where('token_hash', hash('sha256', $token))->first();
        if (!$record || ($record->expires_at && $record->expires_at->isPast())) return response()->json(['message' => 'Unauthenticated.'], 401);
        $record->forceFill(['last_used_at' => now()])->save();
        $request->setUserResolver(fn () => $record->user);
        $request->attributes->set('apiToken', $record);
        return $next($request);
    }
}
