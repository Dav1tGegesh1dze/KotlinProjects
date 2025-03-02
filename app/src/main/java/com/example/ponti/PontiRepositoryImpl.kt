package com.example.ponti

import com.example.ponti.domain.Category
import com.example.ponti.domain.Event
import javax.inject.Inject

class PontiRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val apiHelper: ApiHelper
) : PontiRepository {

    override suspend fun getCategories(): Resource<List<Category>> {
        val response = apiHelper.handleHttpRequest { apiService.getAllData() }
        return when (response) {
            is Resource.Success -> Resource.Success(response.data.toCategoryList())
            is Resource.Error -> Resource.Error(response.errorMessage)
            is Resource.Loading -> Resource.Loading(response.message)
        }
    }

    override suspend fun getEvents(): Resource<List<Event>> {
        val response = apiHelper.handleHttpRequest { apiService.getAllData() }
        return when (response) {
            is Resource.Success -> Resource.Success(response.data.toEventList())
            is Resource.Error -> Resource.Error(response.errorMessage)
            is Resource.Loading -> Resource.Loading(response.message)
        }
    }
}