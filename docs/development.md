# Development guide

Prerequisites: Docker Desktop with Compose v2; Android Studio, Android SDK Platform 35 or later, Platform Tools, emulator image, and JDK 17.

No Windows PHP, Composer, PostgreSQL, or Redis install is required. Copy backend/.env.example to backend/.env, then run docker compose up --build from backend/. Health endpoint: GET http://localhost:8000/api/v1/health. Tests: docker compose run --rm api php artisan test.

Open android/ in Android Studio. Debug API traffic uses http://10.0.2.2:8000/ for the host machine. Run ./gradlew testDebugUnitTest when the Gradle/Android toolchain is installed.
