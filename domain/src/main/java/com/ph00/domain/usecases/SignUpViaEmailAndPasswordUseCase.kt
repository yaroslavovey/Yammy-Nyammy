package com.ph00.domain.usecases

import com.ph00.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow

class SignUpViaEmailAndPasswordUseCase(private val authRepository: AuthRepository) {

    fun execute(email: String, password: String): Flow<Unit> =
        authRepository.signUpViaEmailAndPassword(email, password)

}