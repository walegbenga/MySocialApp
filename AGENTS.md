# MySocialApp contributor guide

## Architecture and scope
This monorepo contains an Android Kotlin/Compose client in android/, a Laravel REST API in backend/, and documentation in docs/. Public API routes are versioned under /api/v1. Docker is the local runtime for PHP, Composer, PostgreSQL, and Redis; do not introduce a Windows PHP requirement.

Phase 1 has no social-product domain features. Add identity, profiles, social graph, publishing, engagement, communities, discovery/feed, notifications, or messaging only with explicit approval. Use thin controllers, validated requests, application services, policies, and explicit API resources/DTOs as the system grows.

Android follows ui -> ViewModel -> repository -> remote API. Keep composables UI-focused and do not put networking in UI code.

## Principles
- Use idiomatic Kotlin and strict PHP with small cohesive units.
- Preserve backwards compatibility within an API version.
- Keep secrets in environment variables; commit .env.example, never .env.
- Use UTC, pagination, validation, authorization, rate limiting, and least privilege for future public capabilities.
- Never expose internal errors, database models, credentials, or sensitive data in API responses or logs.

## Tests
- Add backend feature tests for endpoints and unit tests for non-trivial services.
- Add Android ViewModel/repository tests and meaningful Compose tests where practical.
- Run docker compose run --rm api php artisan test and applicable Gradle tests. Clearly report when an unavailable environment prevents verification.

## Security
Validate and authorize every future input. Treat media as untrusted, store it in object storage, and validate its content, type, size, and access. Do not commit production data, tokens, private keys, or local environment files.
