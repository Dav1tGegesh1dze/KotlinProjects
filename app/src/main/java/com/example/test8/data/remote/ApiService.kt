package com.example.test8.data.remote

import com.example.test8.data.model.LocationData
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("v3/c4c64996-4ed9-4cbc-8986-43c4990d495a")
    suspend fun getLocations(): Response<List<LocationData>>
}