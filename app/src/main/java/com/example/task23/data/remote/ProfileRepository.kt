package com.example.task23.data.remote

import androidx.paging.*
import com.example.task23.data.local.ProfileDatabase
import com.example.task23.presentation.profile.ProfileItem
import com.example.task23.presentation.profile.ProfileRemoteMediator
import kotlinx.coroutines.flow.Flow

class ProfileRepository(
    private val database: ProfileDatabase,
    private val service: ProfileService
) {
    @OptIn(ExperimentalPagingApi::class)
    fun getProfiles(): Flow<PagingData<ProfileItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 4,
                enablePlaceholders = false,
                initialLoadSize = 4 ,
                prefetchDistance = 1
            ),
            remoteMediator = ProfileRemoteMediator(database, service),
            pagingSourceFactory = { database.profileDao().getProfiles() }
        ).flow
    }

}

