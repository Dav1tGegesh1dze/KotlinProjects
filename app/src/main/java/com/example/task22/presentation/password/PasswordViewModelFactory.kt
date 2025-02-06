package com.example.task22.presentation.password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.task22.data.PinManager

class PasswordViewModelFactory(private val pinManager: PinManager) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PasswordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PasswordViewModel(pinManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}