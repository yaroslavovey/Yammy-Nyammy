package com.ph00.domain.usecases

import com.ph00.domain.repositories.AuthRepository

class GetCurrentUserEmailUseCase(private val authRepository: AuthRepository) {

    fun execute(): String? =
        authRepository.getCurrentUserEmail()

}
