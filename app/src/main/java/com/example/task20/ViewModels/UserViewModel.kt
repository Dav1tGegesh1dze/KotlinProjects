package com.example.task20.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.task20.Client.RetrofitClient
import com.example.task20.UserPagingSource


class UserViewModel : ViewModel() {

    val users = Pager(
        config = PagingConfig(
            pageSize = 6,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { UserPagingSource(RetrofitClient.userApiService) }
    ).flow.cachedIn(viewModelScope)
}
