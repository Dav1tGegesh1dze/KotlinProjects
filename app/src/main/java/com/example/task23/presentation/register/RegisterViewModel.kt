package com.example.task23.presentation.register

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

class RegisterViewModel(private val dataStoreManager: DataStoreManager) : ViewModel() {

    private val _registerState = MutableStateFlow<Resource<AuthResponse>?>(null)
    val registerState: StateFlow<Resource<AuthResponse>?> = _registerState.asStateFlow()

    fun register(email: String, password: String) {
        viewModelScope.launch {
            val response = ApiHelper.handleHttpRequest {
                RetrofitClient.apiService.registerUser(User(email, password))
            }
            _registerState.value = response

            if (response is Resource.Success) {
                dataStoreManager.saveUser(email, password, rememberMe = false)
            }
        }
    }
}

