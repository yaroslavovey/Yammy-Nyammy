package com.ph00.domain.usecases

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthResult
import com.ph00.domain.repositories.AuthRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class SignInViaGoogleUseCase(private val authRepository: AuthRepository) {

    suspend fun execute(signInAccount: GoogleSignInAccount?): AuthResult? =
        withContext(IO) { authRepository.signInViaGoogle(signInAccount) }

}
