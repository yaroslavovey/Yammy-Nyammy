package com.ph00.domain.usecases

import com.ph00.domain.repositories.UserRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GetAllCartProductCountUseCase@Inject constructor(private val userRepository: UserRepository) {

    fun execute(): Observable<Int> =
        userRepository.getAllCartProducts()
            .map { list -> list?.sumBy { it.count } ?: 0 }

}