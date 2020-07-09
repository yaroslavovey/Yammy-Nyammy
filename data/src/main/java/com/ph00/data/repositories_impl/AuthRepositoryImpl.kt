package com.ph00.data.repositories_impl

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.ph00.domain.repositories.AuthRepository
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(private val firebaseAuth: FirebaseAuth) : AuthRepository {

    override fun getCurrentUserUid(): String? = firebaseAuth.currentUser?.uid

    override fun getCurrentUserEmail(): String? = firebaseAuth.currentUser?.email

//    override fun currentUserIsAnonymous(): Boolean? = firebaseAuth.currentUser?.isAnonymous

    override suspend fun signInViaEmailAndPassword(email: String, password: String): Boolean? {
        return try {
            firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .await()
            true
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun signOut() = firebaseAuth.signOut()

    override suspend fun signInAnonymously(): Boolean? {
        return try {
            firebaseAuth
                .signInAnonymously()
                .await()
            true
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun signUpViaEmailAndPassword(email: String, password: String): Boolean? {
        return try {
            firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .await()
            true
        } catch (e: Exception) {
            return null
        }
    }

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

    override suspend fun reauthenticate(email: String, password: String): Boolean? {
        return try {
            val credential = EmailAuthProvider.getCredential(email, password)
            firebaseAuth
                .currentUser
                ?.reauthenticate(credential)
                ?.await()
            true
        } catch (e: Exception) {
            null
        }
    }
//
//    override suspend fun linkCurrentUserWithCredential(email: String, password: String): Boolean? {
//        return try {
//            val credential = EmailAuthProvider.getCredential(email, password)
//            firebaseAuth
//                .currentUser
//                ?.linkWithCredential(credential)
//                ?.await()
//                ?.let { true }
//        } catch (e: Exception) {
//            null
//        }
//    }

}