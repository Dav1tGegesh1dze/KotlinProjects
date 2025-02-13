package com.example.task23

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

@Singleton
class DataStoreManager @Inject constructor(@ApplicationContext private val context: Context) {

    companion object {
        private val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        private val USER_PASSWORD_KEY = stringPreferencesKey("user_password")
        private val REMEMBER_ME_KEY = booleanPreferencesKey("remember_me")
    }

    val userEmail: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[USER_EMAIL_KEY]
    }

    val userPassword: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[USER_PASSWORD_KEY]
    }

    val isRemembered: Flow<Boolean?> = context.dataStore.data.map { prefs ->
        prefs[REMEMBER_ME_KEY]
    }

    suspend fun saveUser(email: String, password: String, rememberMe: Boolean = false) {
        context.dataStore.edit { prefs ->
            prefs[USER_EMAIL_KEY] = email
            prefs[USER_PASSWORD_KEY] = password
            prefs[REMEMBER_ME_KEY] = rememberMe
        }
    }

    suspend fun clearUser() {
        context.dataStore.edit { it.clear() }
    }
}
