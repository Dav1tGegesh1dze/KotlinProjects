package com.example.task23.data.local

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val errorMessage: String) : Resource<Nothing>()

}