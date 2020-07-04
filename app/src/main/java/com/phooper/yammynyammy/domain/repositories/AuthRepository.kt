package com.phooper.yammynyammy.domain.repositories

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun getCurrentUser(): FirebaseUser?
    suspend fun getFirebaseAuth(): FirebaseAuth?
    suspend fun signInViaEmailAndPassword(email: String, password: String): AuthResult?
    suspend fun signInViaGoogle(signInAccount: GoogleSignInAccount?): AuthResult?
    suspend fun signOut()
    suspend fun signUpViaEmailAndPassword(email: String, password: String): AuthResult?
    suspend fun updatePassword(newPassword: String): Boolean?
    suspend fun reauthenticate(user: FirebaseUser, authCredential: AuthCredential): Boolean?
}