package com.example.task17.Parseing

data class User(
    val email: String,
    val password: String
)

data class AuthResponse(
    val token: String
)
