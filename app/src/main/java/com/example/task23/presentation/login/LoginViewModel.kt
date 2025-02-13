package com.example.task23.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task23.data.local.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {
    private val _loginState = MutableStateFlow<Resource<Unit>?>(null)
    val loginState: StateFlow<Resource<Unit>?> = _loginState.asStateFlow()

    val savedEmail = repository.userEmail
    val savedPassword = repository.userPassword
    val isRemembered = repository.isRemembered

    fun login(email: String, password: String, rememberMe: Boolean) {
        viewModelScope.launch {
            try {
                if (email == "eve.holt@reqres.in") {
                    if (rememberMe) {
                        repository.saveUserCredentials(email, password, rememberMe)
                    }
                    _loginState.value = Resource.Success(Unit)
                } else {
                    _loginState.value = Resource.Error("Invalid credentials")
                }
            } catch (e: Exception) {
                _loginState.value = Resource.Error(e.localizedMessage ?: "An error occurred")
            }
        }
    }

    fun clearSession() {
        viewModelScope.launch {
            repository.clearUserCredentials()
            _loginState.value = null
        }
    }

    fun resetLoginState() {
        _loginState.value = null
    }
}