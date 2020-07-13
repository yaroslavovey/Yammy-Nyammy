package com.ph00.domain.usecases

import com.ph00.domain.repositories.UserRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat

@FlowPreview
class RemoveProductsFromCartUseCase(private val userRepository: UserRepository) {

    fun execute(productId: Int, count: Int): Flow<Unit> =
        userRepository.getCartProductById(productId).flatMapConcat {
            if (it?.count == 1)
                userRepository.deleteCartProductById(productId)
            else
                userRepository.decreaseCartProductCount(productId, count)
        }

}