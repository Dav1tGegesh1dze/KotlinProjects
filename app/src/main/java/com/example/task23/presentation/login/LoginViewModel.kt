package com.example.task23.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task23.DataStoreManager
import com.example.task23.data.local.AuthResponse
import com.example.task23.data.local.Resource
import com.example.task23.data.local.User
import com.example.task23.data.remote.ApiHelper
import com.example.task23.data.remote.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val dataStoreManager: DataStoreManager) : ViewModel() {
    private val _loginState = MutableStateFlow<Resource<AuthResponse>?>(null)
    val loginState: StateFlow<Resource<AuthResponse>?> = _loginState.asStateFlow()

    val savedEmail = dataStoreManager.userEmail
    val savedPassword = dataStoreManager.userPassword
    val isRemembered = dataStoreManager.isRemembered

    fun login(email: String, password: String, rememberMe: Boolean) {
        viewModelScope.launch {
            val response = ApiHelper.handleHttpRequest {
                RetrofitClient.apiService.login(User(email, password))
            }
            _loginState.value = response

            if (response is Resource.Success) {
                dataStoreManager.saveUser(email, password, rememberMe)
            }
        }
    }

    fun clearSession() {
        viewModelScope.launch {
            dataStoreManager.clearUser()
        }
    }
}

