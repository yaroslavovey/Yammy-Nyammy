package com.ph00.domain.repositories

interface AuthRepository {
    fun getCurrentUserUid(): String?
    fun getCurrentUserEmail(): String?
//    fun currentUserIsAnonymous(): Boolean?
    suspend fun signInViaEmailAndPassword(email: String, password: String): Boolean?
    suspend fun signUpViaEmailAndPassword(email: String, password: String): Boolean?
    suspend fun signInAnonymously(): Boolean?
    suspend fun signOut()
    suspend fun updatePassword(newPassword: String): Boolean?
    suspend fun reauthenticate(email: String, password: String): Boolean?
//    suspend fun linkCurrentUserWithCredential(email: String, password: String): Boolean?
}