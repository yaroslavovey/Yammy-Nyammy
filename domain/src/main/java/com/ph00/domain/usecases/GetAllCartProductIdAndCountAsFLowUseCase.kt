package com.ph00.domain.usecases

import com.ph00.domain.repositories.UserRepository

class GetAllCartProductIdAndCountAsFLowUseCase(private val userRepository: UserRepository) {

    fun execute() = userRepository.getAllCartProductsFlow()

}