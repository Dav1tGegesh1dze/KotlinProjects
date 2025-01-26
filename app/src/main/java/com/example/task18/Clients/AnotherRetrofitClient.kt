package com.example.task18.Clients


import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ProfileServiceUser {
    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int = 1
    ): Response<UserResponse>
}

object AnotherRetrofitClient {
    private const val BASE_URL = "https://reqres.in/api/"

    val retrofit: ProfileServiceUser by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProfileServiceUser::class.java)
    }
}