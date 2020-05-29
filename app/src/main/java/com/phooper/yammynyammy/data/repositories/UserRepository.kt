package com.phooper.yammynyammy.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class UserRepository(private val firebaseAuth: FirebaseAuth) {

    suspend fun getCurrentUser(): FirebaseUser? = withContext(IO) { firebaseAuth.currentUser }

}