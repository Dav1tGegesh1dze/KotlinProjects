package com.example.task20

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.task20.Client.User
import com.example.task20.Client.UserApiService
import retrofit2.HttpException
import java.io.IOException

class UserPagingSource(
    private val apiService: UserApiService
) : PagingSource<Int, User>() {
    //default
    var perPage: Int = 6

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val currentPage = params.key ?: 1

        return try {
            val response = apiService.getUsers(currentPage)
            if (response.isSuccessful) {
                val data = response.body()?.data ?: emptyList()
                val nextPage = if (data.isEmpty()) null else currentPage + 1
                val perPage = response.body()?.perPage ?: 6

                LoadResult.Page(
                    data = data,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = nextPage
                )
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition
    }
}
