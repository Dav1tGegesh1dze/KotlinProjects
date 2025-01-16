package com.example.task16.ParentRecycler

import com.example.task16.ChildRecycler.Field
import kotlinx.serialization.Serializable

@Serializable
data class FieldGroup(
    val groupId: Int,
    val fields: List<Field>
)