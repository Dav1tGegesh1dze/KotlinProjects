package com.example.test6.data.remote
import com.example.test6.data.local.TourItem
import retrofit2.Response
import retrofit2.http.GET

interface TourApi {
    @GET("v3/6dffd14a-836f-4566-b024-bd41ace3a874")
    suspend fun getTours(): Response<List<TourItem>>
}