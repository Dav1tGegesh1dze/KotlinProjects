package com.example.task23.presentation.register

import com.example.task23.DataStoreManager
import com.example.task23.data.local.AuthResponse
import com.example.task23.data.local.Resource
import com.example.task23.data.local.User
import com.example.task23.data.remote.ApiHelper

import com.example.task23.data.remote.ProfileService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegisterRepository @Inject constructor(
    private val apiService: ProfileService,
    private val dataStoreManager: DataStoreManager,
    private val apiHelper: ApiHelper
) {
    suspend fun register(email: String, password: String): Resource<AuthResponse> =
        apiHelper.handleHttpRequest {
            apiService.registerUser(User(email, password))
        }

    suspend fun saveUserCredentials(email: String, password: String) {
        dataStoreManager.saveUser(email, password, rememberMe = false)
    }
}
