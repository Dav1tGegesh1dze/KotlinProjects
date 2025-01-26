package com.example.task18.Clients

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ProfileService {

    @POST("login")
    fun login(@Body user: User): Call<AuthResponse>

    @POST("register")
    fun register(@Body user: User): Call<AuthResponse>
}
