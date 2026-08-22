package com.mysocialapp.android.ui.auth

import com.mysocialapp.android.data.remote.AuthResponse
import com.mysocialapp.android.data.remote.UserDto
import com.mysocialapp.android.data.repository.AuthRepository
import com.mysocialapp.android.data.repository.AuthTokenStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    @Before fun setUp() { Dispatchers.setMain(dispatcher) }
    @After fun tearDown() { Dispatchers.resetMain() }
    @Test fun login_persists_token_and_exposes_user() = runTest {
        val user = UserDto(1, "ada", "Ada", "ada@example.test")
        val repository = object : AuthRepository { override suspend fun login(email: String, password: String) = Result.success(AuthResponse(user, "token")); override suspend fun register(username: String, name: String, email: String, password: String) = Result.failure<AuthResponse>(IllegalStateException()); override suspend fun me() = Result.success(user); override suspend fun update(user: UserDto) = Result.success(user); override suspend fun logout() = Result.success(Unit) }
        val storage = object : AuthTokenStorage { var value: String? = null; override fun token() = value; override fun save(token: String) { value = token }; override fun clear() { value = null } }
        val viewModel = AuthViewModel(repository, storage); dispatcher.scheduler.advanceUntilIdle(); viewModel.login("ada@example.test", "SecurePass1"); advanceUntilIdle()
        assertEquals("token", storage.value); assertEquals("ada", viewModel.state.value.user?.username)
    }
}
