package com.example.task18.Fragments

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.task18.Clients.AuthResponse
import com.example.task18.Clients.RetrofitClient
import com.example.task18.Clients.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val _loginResult = MutableLiveData<String>()
    val loginResult: LiveData<String> get() = _loginResult

    private val _registerResult = MutableLiveData<String>()
    val registerResult: LiveData<String> get() = _registerResult

    // register
    fun register(email: String, password: String) {
        val user = User(email, password)

        RetrofitClient.retrofit.register(user).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful) {
                    viewModelScope.launch {
                        try {
                            //save in data manager
                            DataStoreManager.saveUserToPreferences(getApplication(), email, password)
                            _registerResult.postValue("Registration Successful")
                        } catch (e: Exception) {
                            _registerResult.postValue("Error saving user data: ${e.message}")
                            e.printStackTrace()
                        }
                    }
                } else {
                    _registerResult.postValue("Registration Failed: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                _registerResult.postValue("Error: ${t.message}")
                t.printStackTrace()
            }
        })
    }

    // Login
    fun login(email: String, password: String, rememberMe: Boolean = false) {
        viewModelScope.launch {
            val storedEmail = DataStoreManager.getStoredEmail(getApplication())
            val storedPassword = DataStoreManager.getStoredPassword(getApplication())

            if (storedEmail == null || storedPassword == null) {
                _loginResult.postValue("No user is registered yet")
            } else if (email == storedEmail && password == storedPassword) {
                DataStoreManager.saveRememberMe(getApplication(), rememberMe)
                _loginResult.postValue("Login Successful")
            } else {
                _loginResult.postValue("Invalid Email or Password")
            }
        }
    }

    // Logout
    fun logout() {
        viewModelScope.launch {
            try {
                DataStoreManager.clearAllData(getApplication())
                _loginResult.postValue("Logged Out")
            } catch (e: Exception) {
                Log.e("LogoutError", "Failed to clear DataStore", e)
                _loginResult.postValue("Logout Failed")
            }
        }
    }

    // get email for homeFragment
    suspend fun getStoredEmail(): String? {
        return DataStoreManager.getStoredEmail(getApplication())
    }
}
