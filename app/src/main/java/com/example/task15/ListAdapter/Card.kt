package com.example.task15.ListAdapter

data class Card(
    val id: Int,
    val cardNumber: Long,
    val ownerName: String,
    val expiryDate: Int,
    val cardImage: cardBackgrond,
    val ccv: String
    )

enum class cardBackgrond{
    Visa,
    Mastercard
}
