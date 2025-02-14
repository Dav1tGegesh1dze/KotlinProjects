package com.example.test6.presentation.tour

import com.example.test6.Resource
import com.example.test6.data.remote.TourApi
import com.example.test6.data.local.TourItem
import com.example.test6.data.remote.ApiHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class TourRepositoryImpl @Inject constructor(
    private val tourApi: TourApi,
    private val apiHelper: ApiHelper
) : TourRepository {

    override fun getTours(): Flow<Resource<List<TourItem>>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiHelper.handleHttpRequest { tourApi.getTours() }
            emit(response)
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error occurred"))
        }
    }
}