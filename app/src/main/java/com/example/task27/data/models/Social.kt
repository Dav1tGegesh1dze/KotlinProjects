package com.example.task27.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StoryDto(
    val id: Int,
    val cover: String,
    val title: String
)

@Serializable
data class PostDto(
    val id: Int,
    val images: List<String>?= null,
    val title: String,
    val comments: Int,
    val likes: Int,
    @SerialName("share_content")
    val shareContent: String,
    val owner: OwnerDto
)

@Serializable
data class OwnerDto(
    @SerialName("first_name")
    val firstName: String,
    @SerialName("last_name")
    val lastName: String,
    val profile: String?,
    @SerialName("post_date")
    val postDate: Long
)
