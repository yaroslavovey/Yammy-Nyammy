package com.phooper.yammynyammy.domain.usecases

import com.google.firebase.auth.AuthResult
import com.phooper.yammynyammy.domain.repositories.AuthRepository
import com.phooper.yammynyammy.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class SignUpViaEmailAndPasswordUseCase(private val authRepository: AuthRepository) {

    suspend fun execute(email: String, password: String): AuthResult? =
        withContext(IO) { authRepository.signUpViaEmailAndPassword(email, password) }

}