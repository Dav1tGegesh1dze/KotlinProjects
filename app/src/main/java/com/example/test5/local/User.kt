package com.example.test5.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: Int,
    val avatar: String?,
    val first_name: String,
    val last_name: String,
    val about: String?,
    val activation_status: Double
)
