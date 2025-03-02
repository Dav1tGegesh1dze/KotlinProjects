package com.example.ponti

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PontiResponse(
    val categories:List<String>,
    val events:List<EventDto>
)

@Serializable
data class EventDto(
    val id: Int,
    val title: String,
    val category: String,
    @SerialName("start_price")
    val startPrice: Double,
    @SerialName("end_price")
    val endPrice: Double,
    val date: String,
    val location: String,
    val description: String,
    @SerialName("image_url")
    val imageUrl: String
)


