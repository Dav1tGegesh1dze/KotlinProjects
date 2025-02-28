package com.example.test8.data.repository

import com.example.test8.data.remote.ApiHelper
import com.example.test8.data.remote.ApiService
import com.example.test8.utils.Resource
import com.example.test8.data.model.LocationData
import javax.inject.Inject


interface LocationRepository {
    suspend fun getLocations(): Resource<List<LocationData>>
}

class LocationRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val apiHelper: ApiHelper
) : LocationRepository {

    override suspend fun getLocations(): Resource<List<LocationData>> {
        return apiHelper.handleHttpRequest { apiService.getLocations() }
    }
}