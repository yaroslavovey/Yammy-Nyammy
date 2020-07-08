package com.ph00.domain.usecases

import com.ph00.domain.repositories.AuthRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class SignOutUseCase(private val authRepository: AuthRepository) {

    suspend fun execute() =
        withContext(IO) { authRepository.signOut() }

}