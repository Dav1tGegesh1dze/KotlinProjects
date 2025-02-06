package com.example.task22.data

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class PinManager(context: Context) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val encryptedSharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secure_pin_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun savePin(pin: String) {
        encryptedSharedPreferences.edit().putString("user_pin", pin).apply()
    }

    fun getPin(): String? {
        return encryptedSharedPreferences.getString("user_pin", null)
    }

    fun validatePin(inputPin: String): Boolean {
        val savedPin = getPin()
        return savedPin == inputPin
    }
}