package com.example.ponti

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthServiceImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthService {

    override suspend fun signUpWithEmail(email: String, password: String): FirebaseUser? =
        withContext(Dispatchers.IO) {
            try {
                auth.createUserWithEmailAndPassword(email, password).await().user
            } catch (e: Exception) {
                throw AuthException("Sign up failed: ${e.message}")
            }
        }

    override suspend fun signInWithEmail(email: String, password: String): FirebaseUser? =
        withContext(Dispatchers.IO) {
            try {
                auth.signInWithEmailAndPassword(email, password).await().user
            } catch (e: Exception) {
                throw AuthException("Sign in failed: ${e.message}")
            }
        }

    override suspend fun signInWithGoogle(idToken: String): FirebaseUser? =
        withContext(Dispatchers.IO) {
            try {
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                auth.signInWithCredential(credential).await().user
            } catch (e: Exception) {
                throw AuthException("Google sign in failed: ${e.message}")
            }
        }

    override suspend fun updateUserProfile(user: FirebaseUser, displayName: String) {
        try {
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(displayName)
                .build()
            user.updateProfile(profileUpdates).await()
        } catch (e: Exception) {
            throw AuthException("Profile update failed: ${e.message}")
        }
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override fun getCurrentUser(): FirebaseUser? = auth.currentUser
}