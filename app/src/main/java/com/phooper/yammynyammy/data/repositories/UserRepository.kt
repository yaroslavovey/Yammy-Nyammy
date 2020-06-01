package com.phooper.yammynyammy.data.repositories

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class UserRepository(private val firebaseAuth: FirebaseAuth) {

    suspend fun getCurrentUser(): FirebaseUser? = withContext(IO) { firebaseAuth.currentUser }

    suspend fun signInViaEmailAndPassword(email: String, password: String) = withContext(IO) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
    }

    suspend fun signInViaGoogle(signInAccount: GoogleSignInAccount?) = withContext(IO) {
        val credential =
            GoogleAuthProvider.getCredential(signInAccount?.idToken, null)
        firebaseAuth.signInWithCredential(credential)
    }

    suspend fun signOut() = withContext(IO) { firebaseAuth.signOut() }
}