package com.mysocialapp.android.data.repository

import com.mysocialapp.android.data.remote.ApiService

class RemoteHealthRepository(private val api: ApiService) : HealthRepository {
    override suspend fun checkHealth(): Result<String> = runCatching { api.health().status }
}
