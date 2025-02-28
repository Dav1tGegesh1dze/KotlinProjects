package com.example.test8.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LocationData(
    val lat: Double,
    val lan: Double,
    val title: String,
    val address: String
)