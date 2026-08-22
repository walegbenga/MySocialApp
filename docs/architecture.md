# Architecture

Phase 1 establishes only a Compose Android client and Laravel API foundation.

Android UI -> ViewModel -> repository -> Retrofit -> Laravel /api/v1 -> PostgreSQL / Redis.

Transport code stays in data/remote and UI remains network-independent. Laravel controllers stay thin; future domains require routes, services, policies, migrations, and tests. PostgreSQL will be the system of record; Redis supports caches, queues, and rate limits. Object storage will hold media in a later approved phase.
