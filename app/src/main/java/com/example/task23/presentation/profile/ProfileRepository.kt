package com.example.task23.presentation.profile

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.task23.data.local.ProfileDatabase
import com.example.task23.data.remote.ProfileService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(
    private val database: ProfileDatabase,
    private val service: ProfileService
) {
    @OptIn(ExperimentalPagingApi::class)
    fun getProfiles(): Flow<PagingData<ProfileItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 6,
                enablePlaceholders = false,
                initialLoadSize = 12,
                prefetchDistance = 2
            ),
            remoteMediator = ProfileRemoteMediator(database, service),
            pagingSourceFactory = { database.profileDao().getProfiles() }
        ).flow
    }

}