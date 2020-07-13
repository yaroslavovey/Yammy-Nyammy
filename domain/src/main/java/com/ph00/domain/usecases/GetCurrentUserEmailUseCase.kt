package com.ph00.domain.usecases

import com.ph00.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow

class GetCurrentUserEmailUseCase(private val authRepository: AuthRepository) {

    fun execute(): Flow<String> =
        authRepository.getCurrentUserEmail()

}
