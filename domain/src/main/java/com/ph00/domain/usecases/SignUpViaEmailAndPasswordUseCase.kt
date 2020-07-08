package com.ph00.domain.usecases

import com.google.firebase.auth.AuthResult
import com.ph00.domain.repositories.AuthRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class SignUpViaEmailAndPasswordUseCase(private val authRepository: AuthRepository) {

    suspend fun execute(email: String, password: String): AuthResult? =
        withContext(IO) { authRepository.signUpViaEmailAndPassword(email, password) }

}