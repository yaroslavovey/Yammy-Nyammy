package com.ph00.domain.usecases

import com.ph00.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class GetAllCartProductIdAndCountUseCase(private val userRepository: UserRepository) {

    suspend fun execute() = withContext(IO) {
        userRepository.getAllCartProducts()
    }

}