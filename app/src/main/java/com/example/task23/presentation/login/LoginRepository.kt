package com.example.task23.presentation.login

import com.example.task23.DataStoreManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(private val dataStoreManager: DataStoreManager) {

    val userEmail: Flow<String?> = dataStoreManager.userEmail
    val userPassword: Flow<String?> = dataStoreManager.userPassword
    val isRemembered: Flow<Boolean?> = dataStoreManager.isRemembered

    suspend fun saveUserCredentials(email: String, password: String, rememberMe: Boolean) {
        dataStoreManager.saveUser(email, password, rememberMe)
    }

    suspend fun clearUserCredentials() {
        dataStoreManager.clearUser()
    }
}
