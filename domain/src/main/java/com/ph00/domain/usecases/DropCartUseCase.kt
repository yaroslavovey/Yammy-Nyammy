package com.ph00.domain.usecases

import com.ph00.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow

class DropCartUseCase(private val userRepository: UserRepository) {

    fun execute(): Flow<Unit> =
        userRepository.deleteAllCartProducts()

}