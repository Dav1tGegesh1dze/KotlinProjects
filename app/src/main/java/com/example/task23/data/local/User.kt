package com.example.task23.data.local

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val email:String,
    val password:String
)
@Serializable
data class AuthResponse(
    val token: String
)
