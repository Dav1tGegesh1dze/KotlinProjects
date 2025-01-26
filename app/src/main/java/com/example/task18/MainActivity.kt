package com.example.task18

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.task18.databinding.ActivityMainBinding
import dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        lifecycleScope.launch {
            if (isUserLoggedIn()) {
                navController.navigate(R.id.homeFragment)
            }
        }
    }

    private suspend fun isUserLoggedIn(): Boolean {
        val email = DataStoreManager.getStoredEmail(application)
        val rememberLogin = application.dataStore.data.first()[booleanPreferencesKey("remember_login")] ?: false
        return email != null && rememberLogin
    }
}
