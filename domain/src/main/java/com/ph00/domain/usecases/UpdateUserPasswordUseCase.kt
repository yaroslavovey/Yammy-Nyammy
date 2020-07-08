package com.ph00.domain.usecases

import com.ph00.domain.repositories.AuthRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class UpdateUserPasswordUseCase(private val authRepository: AuthRepository) {

    suspend fun execute(newPassword: String) =
        withContext(IO) {
            authRepository.updatePassword(newPassword)
        }

}