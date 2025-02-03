package com.example.test5.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test5.server.NetworkUtils
import com.example.test5.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: UserRepository,
    private val context: Context
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isOfflineData = MutableStateFlow(false)
    val isOfflineData = _isOfflineData.asStateFlow()

    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()

    val users = repository.users

    fun loadUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                val isOnline = NetworkUtils.isOnline(context)
                if (isOnline) {
                    repository.fetchAndStoreUsers()
                    _isOfflineData.value = false
                } else {
                    _isOfflineData.value = true
                }
            } catch (e: Exception) {
                _error.emit(e.message ?: "Unknown error")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
