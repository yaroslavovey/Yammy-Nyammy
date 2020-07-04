package com.phooper.yammynyammy.data.repositories_impl

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.*
import com.phooper.yammynyammy.domain.repositories.AuthRepository
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(private val firebaseAuth: FirebaseAuth) : AuthRepository {

    override suspend fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser

    override suspend fun signInViaEmailAndPassword(email: String, password: String): AuthResult? {
        return try {
            firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .await()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun signInViaGoogle(signInAccount: GoogleSignInAccount?): AuthResult? {
        val credential =
            GoogleAuthProvider.getCredential(signInAccount?.idToken, null)
        return try {
            firebaseAuth
                .signInWithCredential(credential)
                .await()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun signOut() = firebaseAuth.signOut()

    override suspend fun signUpViaEmailAndPassword(email: String, password: String): AuthResult? {
        return try {
            firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .await()
        } catch (e: Exception) {
            return null
        }
    }

    override suspend fun getFirebaseAuth(): FirebaseAuth = firebaseAuth

    override suspend fun updatePassword(newPassword: String): Boolean? {
        return try {
            firebaseAuth
                .currentUser
                ?.updatePassword(newPassword)
                ?.await()
            true
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun reauthenticate(
        user: FirebaseUser,
        authCredential: AuthCredential
    ): Boolean? {
        return try {
            user.reauthenticate(authCredential).await()
            true
        } catch (e: Exception) {
            null
        }
    }
}