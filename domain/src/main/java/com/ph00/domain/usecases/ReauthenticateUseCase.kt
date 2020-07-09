package com.ph00.domain.usecases

import com.ph00.domain.repositories.AuthRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class ReauthenticateUseCase(
    private val getCurrentUserEmailUseCase: GetCurrentUserEmailUseCase,
    private val authRepository: AuthRepository
) {

    suspend fun execute(currentPassword: String) =
        withContext(IO) {
            getCurrentUserEmailUseCase.execute()?.let { email ->
                authRepository.reauthenticate(email, currentPassword)
            }
        }
}