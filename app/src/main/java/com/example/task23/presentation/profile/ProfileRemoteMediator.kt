package com.example.task23.presentation.profile

import androidx.paging.*
import androidx.room.withTransaction
import com.example.task23.data.local.ProfileDatabase

import com.example.task23.data.remote.ProfileService
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class ProfileRemoteMediator(
    private val database: ProfileDatabase,
    private val service: ProfileService
) : RemoteMediator<Int, ProfileItem>() {
    private var currentPage = 1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ProfileItem>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    currentPage = 1
                    currentPage
                }
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    currentPage++
                    currentPage
                }
            }

            val response = service.getProfiles(page)
            if (!response.isSuccessful) {
                return MediatorResult.Error(HttpException(response))
            }

            val profiles = response.body()?.data ?: emptyList()
            val endOfPaginationReached = profiles.isEmpty() ||
                    (response.body()?.page ?: 0) >= (response.body()?.totalPages ?: 0)

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.profileDao().clearAllProfiles()
                }
                database.profileDao().insertProfiles(profiles)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}