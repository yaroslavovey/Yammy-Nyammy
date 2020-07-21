package com.ph00.domain.usecases

import com.ph00.domain.repositories.AuthRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class ReauthenticateUseCase@Inject constructor(
    private val getCurrentUserEmailUseCase: GetCurrentUserEmailUseCase,
    private val authRepository: AuthRepository
) {

    fun execute(currentPassword: String): Completable =
        getCurrentUserEmailUseCase.execute().flatMapCompletable { email ->
            authRepository.reauthenticate(email, currentPassword)
        }

}