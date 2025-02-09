package com.example.task23.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.task23.data.local.ProfileItem
import com.example.task23.presentation.profile.ProfilePagingSource
import kotlinx.coroutines.flow.Flow

class ProfileRepository(private val service: ProfileService) {
    fun getProfiles(): Flow<PagingData<ProfileItem>> {
        return Pager(
            config = PagingConfig(pageSize = 6, enablePlaceholders = false),
            pagingSourceFactory = { ProfilePagingSource(service) }
        ).flow
    }
}
