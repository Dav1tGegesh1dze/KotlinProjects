package com.example.test8.data.remote

import com.example.test8.utils.Resource
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class ApiHelper {
    suspend fun <T> handleHttpRequest(apiCall: suspend () -> Response<T>): Resource<T> {
        val response = apiCall.invoke()
        return try {
            if (response.isSuccessful){
                response.body()?.let {
                    Resource.Success(data = it)
                } ?: Resource.Error(errorMessage = "Something went wrong")
            } else{
                Resource.Error(errorMessage = response.message())
            }
        } catch (throwable: Throwable){
            when (throwable){
                is IOException -> {
                    Resource.Error(errorMessage = throwable.message ?: "")
                }

                is HttpException -> {
                    Resource.Error(errorMessage = throwable.message ?: "")
                }

                is IllegalStateException -> {
                    Resource.Error(errorMessage = throwable.message ?: "")
                }

                else -> {
                    Resource.Error(errorMessage = throwable.message ?: "")
                }

            }
        }

    }
}