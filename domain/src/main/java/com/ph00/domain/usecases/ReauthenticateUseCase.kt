package com.ph00.domain.usecases

import com.ph00.domain.repositories.AuthRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flatMapConcat

@FlowPreview
class ReauthenticateUseCase(
    private val getCurrentUserEmailUseCase: GetCurrentUserEmailUseCase,
    private val authRepository: AuthRepository
) {

    fun execute(currentPassword: String) =
        getCurrentUserEmailUseCase.execute().flatMapConcat { email ->
            authRepository.reauthenticate(email, currentPassword)
        }

}