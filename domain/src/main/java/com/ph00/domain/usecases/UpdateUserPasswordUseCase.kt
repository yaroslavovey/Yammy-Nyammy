package com.ph00.domain.usecases

import com.ph00.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow

class UpdateUserPasswordUseCase(private val authRepository: AuthRepository) {

    fun execute(newPassword: String): Flow<Unit> =
        authRepository.updatePassword(newPassword)

}