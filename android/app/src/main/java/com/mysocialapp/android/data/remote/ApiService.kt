package com.mysocialapp.android.data.remote

import com.squareup.moshi.Json
import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Response

data class HealthResponse(val status: String)
data class UserDto(val id: Long, val username: String, val name: String, val email: String? = null, val bio: String? = null)
data class AuthResponse(val user: UserDto, val token: String)
data class RegisterBody(val username: String, val name: String, val email: String, val password: String, @Json(name = "password_confirmation") val passwordConfirmation: String, @Json(name = "device_name") val deviceName: String = "Android")
data class LoginBody(val email: String, val password: String, @Json(name = "device_name") val deviceName: String = "Android")
data class ProfileBody(val username: String, val name: String, val email: String, val bio: String?)
data class ApiData<T>(val data: T)

interface ApiService {
    @GET("api/v1/health")
    suspend fun health(): HealthResponse
    @POST("api/v1/auth/register") suspend fun register(@Body body: RegisterBody): AuthResponse
    @POST("api/v1/auth/login") suspend fun login(@Body body: LoginBody): AuthResponse
    @POST("api/v1/auth/logout") suspend fun logout(): Response<Unit>
    @GET("api/v1/me") suspend fun me(): ApiData<UserDto>
    @PATCH("api/v1/me") suspend fun updateProfile(@Body body: ProfileBody): ApiData<UserDto>
    @GET("api/v1/users/{username}") suspend fun profile(@Path("username") username: String): ApiData<UserDto>
}
