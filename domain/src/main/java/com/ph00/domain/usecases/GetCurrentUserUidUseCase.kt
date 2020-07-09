package com.ph00.domain.usecases

import com.ph00.domain.repositories.AuthRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class GetCurrentUserUidUseCase(private val authRepository: AuthRepository) {

    fun execute(): String? =
        authRepository.getCurrentUserUid()

}