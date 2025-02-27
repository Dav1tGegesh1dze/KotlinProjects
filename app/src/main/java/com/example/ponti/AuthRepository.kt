package com.example.ponti

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun signUpWithEmail(email: String, password: String, firstName: String, lastName: String, dateOfBirth: String): Result<Unit>
    suspend fun signInWithEmail(email: String, password: String): Result<Unit>
    suspend fun signInWithGoogle(idToken: String): Result<Unit>
    fun getCurrentUser(): FirebaseUser?
    fun signOut()
}