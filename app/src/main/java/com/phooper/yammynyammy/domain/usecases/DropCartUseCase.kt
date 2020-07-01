package com.phooper.yammynyammy.domain.usecases

import com.phooper.yammynyammy.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class DropCartUseCase(private val userRepository: UserRepository) {

    suspend fun execute() {
        withContext(IO) {
            userRepository.deleteAllCartProducts()
        }
    }

}