package com.ph00.domain.usecases

import com.ph00.domain.repositories.AuthRepository

class GetIsUserSignedInUseCase(private val authRepository: AuthRepository) {

    fun execute() = authRepository.getIsUserSignedInFlow()

}