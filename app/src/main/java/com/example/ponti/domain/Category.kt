package com.example.ponti.domain

data class Category(
    val name: String
)

data class Event(
    val id: Int,
    val title: String,
    val category: String,
    val startPrice: Double,
    val endPrice: Double,
    val date: String,
    val location: String,
    val description: String,
    val imageUrl: String
)