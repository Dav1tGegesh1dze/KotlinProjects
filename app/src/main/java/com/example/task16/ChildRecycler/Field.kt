package com.example.task16.ChildRecycler

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Field(
    @SerialName("field_id") val fieldId: Int,
    @SerialName("hint") val hint: String,
    @SerialName("icon") val iconUrl: String
)



