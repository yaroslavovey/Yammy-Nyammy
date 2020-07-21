package com.ph00.domain.usecases

import com.ph00.domain.repositories.AuthRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetCurrentUserUidUseCase@Inject constructor(private val authRepository: AuthRepository) {

    fun execute(): Single<String> = authRepository.getCurrentUserUid()

}