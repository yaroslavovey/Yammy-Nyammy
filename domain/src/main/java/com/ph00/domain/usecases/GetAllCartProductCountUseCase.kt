package com.ph00.domain.usecases

import com.ph00.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllCartProductCountUseCase(private val userRepository: UserRepository) {

    fun execute(): Flow<Int> =
        userRepository.getAllCartProducts()
            .map { list -> list?.sumBy { it.count } ?: 0 }

}