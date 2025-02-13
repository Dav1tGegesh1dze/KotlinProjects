package com.example.task23.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task23.data.local.AuthResponse
import com.example.task23.data.local.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: RegisterRepository
) : ViewModel() {
    private val _registerState = MutableStateFlow<Resource<AuthResponse>?>(null)
    val registerState: StateFlow<Resource<AuthResponse>?> = _registerState.asStateFlow()

    fun register(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.register(email, password)
                _registerState.value = response

                if (response is Resource.Success) {
                    repository.saveUserCredentials(email, password)
                }
            } catch (e: Exception) {
                _registerState.value = Resource.Error(e.message ?: "Registration failed")
            }
        }
    }

    fun resetState() {
        _registerState.value = null
    }
}