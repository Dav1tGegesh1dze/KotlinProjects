package com.example.task22.presentation.password

import androidx.lifecycle.ViewModel
import com.example.task22.data.PinManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PasswordViewModel(private val pinManager: PinManager) : ViewModel() {
    private val _enteredPin = MutableStateFlow("")
    val enteredPin: StateFlow<String> = _enteredPin.asStateFlow()

    fun addNumber(number: String) {
        if (_enteredPin.value.length < 4) {
            _enteredPin.value += number
        }
    }

    fun removeLastNumber() {
        if (_enteredPin.value.isNotEmpty()) {
            _enteredPin.value = _enteredPin.value.dropLast(1)
        }
    }

    fun checkPin(): Boolean {
        return pinManager.validatePin(_enteredPin.value)
    }

    fun clearPin() {
        _enteredPin.value = ""
    }
}