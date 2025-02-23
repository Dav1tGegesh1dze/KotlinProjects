package com.example.task27.data.repository

import com.example.task27.data.models.PostDto
import com.example.task27.data.models.Resource
import com.example.task27.data.models.StoryDto
import com.example.task27.data.remote.ApiHelper
import com.example.task27.data.remote.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(
    private val apiService: ApiService,
    private val apiHelper: ApiHelper
) {
    suspend fun getStories(): Resource<List<StoryDto>> =
        apiHelper.handleHttpRequest { apiService.getStories() }

    suspend fun getPosts(): Resource<List<PostDto>> =
        apiHelper.handleHttpRequest {
            val posts = apiService.getPosts()

            posts
        }
}