package com.example.task23.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.task23.data.local.ProfileItem
import com.example.task23.data.remote.ProfileRepository
import kotlinx.coroutines.flow.Flow



class ProfileViewModel(private val repository: ProfileRepository) : ViewModel() {
    val profiles: Flow<PagingData<ProfileItem>> = repository.getProfiles().cachedIn(viewModelScope)

    class Factory(private val repository: ProfileRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ProfileViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

