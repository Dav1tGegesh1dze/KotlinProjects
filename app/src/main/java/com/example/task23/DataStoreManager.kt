package com.example.task23

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class DataStoreManager(private val context: Context) {
    companion object {
        private val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        private val USER_PASSWORD_KEY = stringPreferencesKey("user_password")
        private val REMEMBER_ME_KEY = stringPreferencesKey("remember_me")
    }

    val userEmail: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[USER_EMAIL_KEY]
    }

    val userPassword: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[USER_PASSWORD_KEY]
    }

    val isRemembered: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[REMEMBER_ME_KEY]
    }

    suspend fun saveUser(email: String, password: String, rememberMe: Boolean = false) {
        context.dataStore.edit { prefs ->
            prefs[USER_EMAIL_KEY] = email
            prefs[USER_PASSWORD_KEY] = password
            prefs[REMEMBER_ME_KEY] = rememberMe.toString()
        }
    }

    suspend fun clearUser() {
        context.dataStore.edit { it.clear() }
    }
}