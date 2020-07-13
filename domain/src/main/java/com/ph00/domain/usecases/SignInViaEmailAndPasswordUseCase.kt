package com.ph00.domain.usecases

import com.ph00.domain.repositories.AuthRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class SignInViaEmailAndPasswordUseCase(private val authRepository: AuthRepository) {

    fun execute(email: String, password: String): Flow<Unit> =
        authRepository.signInViaEmailAndPassword(email, password)

}