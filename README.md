# MySocialApp

Production-oriented social platform monorepo. Phase 1 provides only an Android Kotlin/Compose starter and a Docker-first Laravel REST API foundation—no social-media features.

## Layout
android/  Kotlin / Jetpack Compose application
backend/  Laravel API and Docker development stack
docs/     Architecture and development instructions

## Start the backend
Start Docker Desktop, then from backend/:
docker compose up --build

Health check: GET http://localhost:8000/api/v1/health
Backend tests: docker compose run --rm api php artisan test

## Start Android
Open android/ in Android Studio after installing its Android SDK and JDK 17. Debug builds use http://10.0.2.2:8000/, the Android-emulator address for the host machine.

See docs/architecture.md and docs/development.md.
