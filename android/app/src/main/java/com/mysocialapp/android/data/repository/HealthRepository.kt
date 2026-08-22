package com.mysocialapp.android.data.repository

interface HealthRepository { suspend fun checkHealth(): Result<String> }
