package com.example.ponti

import com.example.ponti.domain.Category
import com.example.ponti.domain.Event

interface PontiRepository {
    suspend fun getCategories(): Resource<List<Category>>
    suspend fun getEvents(): Resource<List<Event>>
}