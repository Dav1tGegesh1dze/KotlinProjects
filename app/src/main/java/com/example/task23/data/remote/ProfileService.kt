package com.example.task23.data.remote

import com.example.task23.data.local.AuthResponse
import com.example.task23.data.local.ProfilesResponse
import com.example.task23.data.local.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProfileService {
    @POST("register")
    suspend fun registerUser(@Body user: User): Response<AuthResponse>

    @POST("login")
    suspend fun login(@Body user: User): Response<AuthResponse>

    @GET("users")
    suspend fun getProfiles(@Query("page") page: Int): Response<ProfilesResponse>
}
