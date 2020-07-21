package com.ph00.domain.usecases

import com.ph00.domain.repositories.AuthRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GetIsUserSignedInUseCase @Inject constructor(private val authRepository: AuthRepository) {

    fun execute(): Observable<Boolean> = authRepository.getIsUserSignedInObservable()

}