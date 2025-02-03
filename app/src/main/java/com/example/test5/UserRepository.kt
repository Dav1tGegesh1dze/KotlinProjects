package com.example.test5

import com.example.test5.local.User
import com.example.test5.local.UserDao
import com.example.test5.server.ApiService
import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val api: ApiService,
    private val userDao: UserDao
) {
    val users: Flow<List<User>> = userDao.getAllUsers()

    suspend fun fetchAndStoreUsers() {
        try {
            val response = api.getUsers()
            userDao.insertUsers(response.users)

        } catch (e: Exception) {
            throw e
        }
    }
}