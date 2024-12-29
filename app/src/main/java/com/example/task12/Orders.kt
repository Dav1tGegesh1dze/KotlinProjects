package com.example.task12

import java.sql.Time

data class Orders(
    val id: Int,
    val order: String,
    val time: Time,
    val trackingNumber: String,
    val quantity: Int,
    val price: Int,
    var status: String
)
