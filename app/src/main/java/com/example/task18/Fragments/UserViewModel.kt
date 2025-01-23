package com.example.task18.Fragments

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.task18.Parseing.AuthResponse
import com.example.task18.Parseing.RetrofitClient
import com.example.task18.Parseing.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPreferences = application.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    private val _loginResult = MutableLiveData<String>()
    val loginResult: LiveData<String> get() = _loginResult

    private val _registerResult = MutableLiveData<String>()
    val registerResult: LiveData<String> get() = _registerResult

    fun register(email: String, password: String) {
        val user = User(email, password)

        RetrofitClient.retrofit.register(user).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful) {
                    saveUserToPreferences(email, password)
                    _registerResult.postValue("Registration Successful")
                } else {
                    _registerResult.postValue("Registration Failed: API error")
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                _registerResult.postValue("Error: ${t.message}")
            }
        })
    }

    fun login(email: String, password: String, rememberMe: Boolean = false) {
        Log.d("UserViewModel", "Attempting login with email: $email, password: $password")

        val storedEmail = sharedPreferences.getString("email", null)
        val storedPassword = sharedPreferences.getString("password", null)

        if (storedEmail == null || storedPassword == null) {
            _loginResult.postValue("No user is registered yet")
        } else if (email == storedEmail && password == storedPassword) {
            // Save remember me
            sharedPreferences.edit()
                .putBoolean("remember_login", rememberMe)
                .apply()

            _loginResult.postValue("Login Successful")
        } else {
            _loginResult.postValue("Invalid Email or Password")
        }
    }

    fun logout() {
        // clear remember me
        sharedPreferences.edit()
            .remove("remember_login")
            .apply()
    }

    private fun saveUserToPreferences(email: String, password: String) {
        sharedPreferences.edit()
            .putString("email", email)
            .putString("password", password)
            .apply()
    }

    fun getStoredEmail(): String {
        return sharedPreferences.getString("email", "User") ?: "User"
    }
}