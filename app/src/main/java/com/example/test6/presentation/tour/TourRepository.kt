package com.example.test6.presentation.tour

import com.example.test6.Resource
import com.example.test6.data.local.TourItem
import kotlinx.coroutines.flow.Flow

interface TourRepository {
    fun getTours(): Flow<Resource<List<TourItem>>>
}
