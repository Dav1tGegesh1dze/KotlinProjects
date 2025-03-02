package com.example.ponti

import retrofit2.Response
import retrofit2.http.GET



interface ApiService {
    @GET("db")
    suspend fun getAllData(): Response<PontiResponse>
}