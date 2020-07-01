package com.phooper.yammynyammy.domain.usecases

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthResult
import com.phooper.yammynyammy.domain.repositories.AuthRepository
import com.phooper.yammynyammy.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class SignInViaGoogleUseCase(private val authRepository: AuthRepository) {

    suspend fun execute(signInAccount: GoogleSignInAccount?): AuthResult? =
        withContext(IO) { authRepository.signInViaGoogle(signInAccount) }

}
