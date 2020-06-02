package com.phooper.yammynyammy.data.repositories

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.phooper.yammynyammy.data.models.User
import com.phooper.yammynyammy.utils.Constants.Companion.USERS
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class UserRepository(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) {

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

    suspend fun registerWithEmailAndPassword(email: String, password: String) =
        withContext(IO) { firebaseAuth.createUserWithEmailAndPassword(email, password) }

    suspend fun addCurrentUserData(data: User) = withContext(IO) {
        firebaseAuth.currentUser?.uid?.let { uid ->
            firebaseDatabase.getReference(USERS).child(uid).setValue(data)
        }
    }

    suspend fun getCurrentUserData() =
        withContext(IO) {
            firebaseAuth.currentUser?.uid?.let { uid ->
                firebaseDatabase.reference.child(
                    USERS
                ).child(uid)
            }
        }
}