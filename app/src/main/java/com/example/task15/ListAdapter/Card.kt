package com.example.task15.ListAdapter

data class Card(
    val id: Int,
    val cardNumber: String,
    val ownerName: String,
    val expiryDate: String,
    val cardImage: cardBackgrond,
    val ccv: String
    )

enum class cardBackgrond{
    Visa,
    Mastercard
}
