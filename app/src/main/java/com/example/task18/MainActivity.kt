package com.example.task18

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.task18.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        if (isUserLoggedIn()) {
            navController.navigate(R.id.homeFragment)
        }
    }

    private fun isUserLoggedIn(): Boolean {
        return sharedPreferences.getString("email", null) != null &&
                sharedPreferences.getBoolean("remember_login", false)
    }
}