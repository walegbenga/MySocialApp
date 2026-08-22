package com.mysocialapp.android.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mysocialapp.android.data.repository.HealthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HomeUiState(val healthStatus: String = "Not checked", val isLoading: Boolean = false)

class HomeViewModel(private val repository: HealthRepository) : ViewModel() {
    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()
    fun checkApi() = viewModelScope.launch {
        _state.value = _state.value.copy(isLoading = true)
        _state.value = try { HomeUiState(repository.checkHealth().getOrThrow()) } catch (_: Exception) { HomeUiState("Unavailable") }
    }
}
