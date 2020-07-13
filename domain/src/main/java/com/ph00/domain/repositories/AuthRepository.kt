package com.ph00.domain.repositories

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getCurrentUserUid(): Flow<String>
    fun getCurrentUserEmail(): Flow<String>
    fun signOut(): Flow<Unit>
    fun getIsUserSignedInFlow(): Flow<Boolean>
    fun signInViaEmailAndPassword(email: String, password: String): Flow<Unit>
    fun signUpViaEmailAndPassword(email: String, password: String): Flow<Unit>
    fun updatePassword(newPassword: String): Flow<Unit>
    fun reauthenticate(email: String, password: String): Flow<Unit>
}