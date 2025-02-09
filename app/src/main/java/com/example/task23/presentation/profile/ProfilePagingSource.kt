package com.example.task23.presentation.profile

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.task23.data.local.ProfileItem
import com.example.task23.data.remote.ProfileService


class ProfilePagingSource(
    private val service: ProfileService
) : PagingSource<Int, ProfileItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProfileItem> {
        val page = params.key ?: 1
        return try {
            val response = service.getProfiles(page)
            if (response.isSuccessful) {
                val data = response.body()?.data ?: emptyList()
                LoadResult.Page(
                    data = data,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (data.isEmpty()) null else page + 1
                )
            } else {
                LoadResult.Error(Exception(response.message()))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ProfileItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
