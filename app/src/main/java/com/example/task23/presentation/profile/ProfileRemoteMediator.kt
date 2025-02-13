package com.example.task23.presentation.profile

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.task23.data.local.ProfileDatabase
import com.example.task23.data.remote.ProfileService

@OptIn(ExperimentalPagingApi::class)
class ProfileRemoteMediator(
    private val database: ProfileDatabase,
    private val service: ProfileService
) : RemoteMediator<Int, ProfileItem>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ProfileItem>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (state.pages.size + 1)
                    }
                }
            }

            val response = service.getProfiles(page)
            if (response.isSuccessful) {
                val profiles = response.body()?.data ?: emptyList()

                if (loadType == LoadType.REFRESH) {
                    database.profileDao().clearAllProfiles()
                }

                database.profileDao().insertProfiles(profiles)

                MediatorResult.Success(
                    endOfPaginationReached = profiles.isEmpty()
                )
            } else {
                MediatorResult.Error(Exception("API call failed"))
            }
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}