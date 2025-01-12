package com.example.task14.ListAdapter

import java.io.Serializable

data class Order(
    val id: Int,
    val title: String,
    val color: Int,
    val colorName: String,
    val quantity: Int,
    val status: OrderStatus,
    val price: Double,
    val imageRes: Int
): Serializable

enum class OrderStatus {
    ACTIVE, COMPLETED
}

