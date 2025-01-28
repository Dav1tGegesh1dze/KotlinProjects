package com.example.task20.Client

import com.google.gson.annotations.SerializedName


data class UserResponse(
    val totalPages: Int,
    @SerializedName("per_page") val perPage: Int,
    val data: List<User>
)


data class User(
    val id: Int,
    val email: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    val avatar: String
)
