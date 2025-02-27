package com.example.ponti

import com.google.firebase.auth.FirebaseUser

interface AuthService {
    suspend fun signUpWithEmail(email: String, password: String): FirebaseUser?
    suspend fun signInWithEmail(email: String, password: String): FirebaseUser?
    suspend fun signInWithGoogle(idToken: String): FirebaseUser?
    suspend fun updateUserProfile(user: FirebaseUser, displayName: String)
    suspend fun signOut()
    fun getCurrentUser(): FirebaseUser?
}
class AuthException(message: String) : Exception(message)