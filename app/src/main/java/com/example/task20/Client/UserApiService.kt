package com.example.task20.Client

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApiService {
    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int
    ): Response<UserResponse>
}

