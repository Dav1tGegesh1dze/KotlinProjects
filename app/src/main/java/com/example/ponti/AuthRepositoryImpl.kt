package com.example.ponti

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {

    override suspend fun signUpWithEmail(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        dateOfBirth: String
    ): Result<Unit> = try {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        result.user?.let { user ->
            val profileUpdates = userProfileChangeRequest {
                displayName = "$firstName $lastName"
            }
            user.updateProfile(profileUpdates).await()
        }
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun signInWithEmail(email: String, password: String): Result<Unit> = try {
        auth.signInWithEmailAndPassword(email, password).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun signInWithGoogle(idToken: String): Result<Unit> = try {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override fun getCurrentUser(): FirebaseUser? = auth.currentUser

    override fun signOut() = auth.signOut()
}