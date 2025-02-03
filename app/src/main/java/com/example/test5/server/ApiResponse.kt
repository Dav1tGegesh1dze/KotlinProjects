package com.example.test5.server

import com.example.test5.local.User
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResponse(
    val status: Boolean,
    val additional_data: Any?,
    val options: Any?,
    val permissions: List<String?>,
    val users: List<User>
)
