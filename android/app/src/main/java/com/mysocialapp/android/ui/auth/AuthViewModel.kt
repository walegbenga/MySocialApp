package com.mysocialapp.android.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mysocialapp.android.data.remote.UserDto
import com.mysocialapp.android.data.repository.AuthRepository
import com.mysocialapp.android.data.repository.AuthTokenStorage
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class AuthUiState(val loading: Boolean = true, val user: UserDto? = null, val error: String? = null)
class AuthViewModel(private val repository: AuthRepository, private val tokens: AuthTokenStorage) : ViewModel() {
    private val _state = MutableStateFlow(AuthUiState()); val state = _state.asStateFlow()
    init { restore() }
    fun restore() = viewModelScope.launch { if (tokens.token() == null) _state.value = AuthUiState(loading = false) else _state.value = repository.me().fold({ AuthUiState(false, it) }, { tokens.clear(); AuthUiState(false) }) }
    fun login(email: String, password: String) = authenticate { repository.login(email, password) }
    fun register(username: String, name: String, email: String, password: String) = authenticate { repository.register(username, name, email, password) }
    private fun authenticate(action: suspend () -> Result<com.mysocialapp.android.data.remote.AuthResponse>) = viewModelScope.launch { _state.value = _state.value.copy(loading = true, error = null); _state.value = action().fold({ tokens.save(it.token); AuthUiState(false, it.user) }, { AuthUiState(false, error = "We couldn't complete that. Please check your details and try again.") }) }
    fun update(user: UserDto) = viewModelScope.launch { _state.value = _state.value.copy(loading = true, error = null); _state.value = repository.update(user).fold({ AuthUiState(false, it) }, { _state.value.copy(loading = false, error = "Your changes could not be saved.") }) }
    fun logout() = viewModelScope.launch { repository.logout(); tokens.clear(); _state.value = AuthUiState(loading = false) }
    fun clearError() { _state.value = _state.value.copy(error = null) }
}
