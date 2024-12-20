package com.example.test2
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test2.User


class UserViewModel : ViewModel() {
    private val _users = MutableLiveData<List<User>>()


    fun addUser(user: User) {
        val currentList = _users.value?.toMutableList() ?: mutableListOf()
        currentList.add(user)
        _users.value = currentList
    }

    // get all users
    fun getUsers(): List<User> {
        return _users.value ?: emptyList()
    }
}