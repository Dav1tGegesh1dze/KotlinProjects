package com.example.task27.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task27.data.repository.HomeRepository
import com.example.task27.data.models.PostDto
import com.example.task27.data.models.Resource
import com.example.task27.data.models.StoryDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {
    private val _stories = MutableStateFlow<Resource<List<StoryDto>>>(Resource.Success(emptyList()))
    val stories: StateFlow<Resource<List<StoryDto>>> = _stories.asStateFlow()

    private val _posts = MutableStateFlow<Resource<List<PostDto>>>(Resource.Success(emptyList()))
    val posts: StateFlow<Resource<List<PostDto>>> = _posts.asStateFlow()

    fun fetchData() {
        viewModelScope.launch {
            launch { fetchStories() }
            launch { fetchPosts() }
        }
    }

    private suspend fun fetchStories() {
        _stories.value = repository.getStories()
    }

    private suspend fun fetchPosts() {
        _posts.value = repository.getPosts()
    }
}