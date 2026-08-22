package com.mysocialapp.android.data.repository

import com.mysocialapp.android.data.remote.*

interface AuthRepository {
    suspend fun register(username: String, name: String, email: String, password: String): Result<AuthResponse>
    suspend fun login(email: String, password: String): Result<AuthResponse>
    suspend fun me(): Result<UserDto>
    suspend fun update(user: UserDto): Result<UserDto>
    suspend fun logout(): Result<Unit>
}

class RemoteAuthRepository(private val api: ApiService) : AuthRepository {
    override suspend fun register(username: String, name: String, email: String, password: String) = runCatching { api.register(RegisterBody(username, name, email, password, password)) }
    override suspend fun login(email: String, password: String) = runCatching { api.login(LoginBody(email, password)) }
    override suspend fun me() = runCatching { api.me().data }
    override suspend fun update(user: UserDto) = runCatching { api.updateProfile(ProfileBody(user.username, user.name, user.email.orEmpty(), user.bio)).data }
    override suspend fun logout() = runCatching { api.logout(); Unit }
}
