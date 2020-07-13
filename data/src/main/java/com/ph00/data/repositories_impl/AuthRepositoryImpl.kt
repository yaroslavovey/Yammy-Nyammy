package com.ph00.data.repositories_impl

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.ph00.domain.repositories.AuthRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(private val firebaseAuth: FirebaseAuth) : AuthRepository {

    override fun getCurrentUserUid(): Flow<String> =
        flow { firebaseAuth.currentUser?.uid?.let { emit(it) } }

    override fun getCurrentUserEmail() = flow { firebaseAuth.currentUser?.email?.let { emit(it) } }

    @ExperimentalCoroutinesApi
    override fun getIsUserSignedInFlow(): Flow<Boolean> =
        callbackFlow {
            val subscription = FirebaseAuth.AuthStateListener { auth ->
                offer(auth.currentUser != null)
            }

            firebaseAuth.addAuthStateListener(subscription)

            awaitClose { firebaseAuth.removeAuthStateListener(subscription) }
        }

    override fun signInViaEmailAndPassword(email: String, password: String): Flow<Unit> =
        flow {
            firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .await()
                .let { emit(Unit) }
        }

    override fun signOut() = flow { emit(firebaseAuth.signOut()) }

    override fun signUpViaEmailAndPassword(email: String, password: String): Flow<Unit> = flow {
        firebaseAuth
            .createUserWithEmailAndPassword(email, password)
            .await()
            .let { emit(Unit) }
    }

    override fun updatePassword(newPassword: String): Flow<Unit> = flow {
        firebaseAuth
            .currentUser
            ?.updatePassword(newPassword)
            ?.await()
            ?.let { emit(Unit) }
    }

    override fun reauthenticate(email: String, password: String) = flow {
        val credential = EmailAuthProvider.getCredential(email, password)
        firebaseAuth
            .currentUser
            ?.reauthenticate(credential)
            ?.await()
            ?.let { emit(Unit) }
    }

}