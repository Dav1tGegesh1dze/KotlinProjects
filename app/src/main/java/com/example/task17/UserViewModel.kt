package com.example.task17

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.task17.Parseing.AuthResponse
import com.example.task17.Parseing.RetrofitClient
import com.example.task17.Parseing.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {

    private var registeredUser: User? = null

    private val _loginResult = MutableLiveData<String>()
    val loginResult: LiveData<String> get() = _loginResult

    private val _registerResult = MutableLiveData<String>()
    val registerResult: LiveData<String> get() = _registerResult

    fun register(email: String, password: String) {
        if (email != "eve.holt@reqres.in") {
            _registerResult.postValue("Email should only be: eve.holt@reqres.in")
            return
        }

        val user = User(email, password)
        RetrofitClient.retrofit.register(user)
            .enqueue(object : Callback<AuthResponse> {
                override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                    if (response.isSuccessful) {
                        registeredUser = user
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

    fun login(email: String, password: String) {
        if (registeredUser == null) {
            _loginResult.postValue("No user is registered yet")
            return
        }

        val user = User(email, password)
        RetrofitClient.retrofit.login(user)
            .enqueue(object : Callback<AuthResponse> {
                override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                    if (response.isSuccessful && email == registeredUser?.email && password == registeredUser?.password) {
                        _loginResult.postValue("Login Successful")
                    } else {
                        _loginResult.postValue("Invalid Email or Password")
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    _loginResult.postValue("Error: ${t.message}")
                }
            })
    }
}
