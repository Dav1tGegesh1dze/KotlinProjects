package com.example.ponti

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun signUp(email: String, password: String, firstName: String, lastName: String, dateOfBirth: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = repository.signUpWithEmail(email, password, firstName, lastName, dateOfBirth)
            _authState.value = when {
                result.isSuccess -> AuthState.Success
                result.isFailure -> AuthState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
                else -> AuthState.Error("Unknown error")
            }
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = repository.signInWithEmail(email, password)
            _authState.value = when {
                result.isSuccess -> AuthState.Success
                result.isFailure -> AuthState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
                else -> AuthState.Error("Unknown error")
            }
        }
    }

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = repository.signInWithGoogle(idToken)
            _authState.value = when {
                result.isSuccess -> AuthState.Success
                result.isFailure -> AuthState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
                else -> AuthState.Error("Unknown error")
            }
        }
    }
}