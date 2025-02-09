package com.example.task23.data.local

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileItem(
    val id: Int,
    val email: String,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("last_name")
    val lastName: String,
    val avatar: String
)

@Serializable
data class ProfilesResponse(
    val page: Int,
    @SerialName("per_page") val perPage: Int,
    val total: Int,
    @SerialName("total_pages") val totalPages: Int,
    val data: List<ProfileItem>
)

