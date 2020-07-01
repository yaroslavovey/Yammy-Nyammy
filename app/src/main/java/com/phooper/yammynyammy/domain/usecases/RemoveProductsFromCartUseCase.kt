package com.phooper.yammynyammy.domain.usecases

import com.phooper.yammynyammy.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class RemoveProductsFromCartUseCase(private val userRepository: UserRepository) {

    suspend fun execute(productId: Int, count: Int) =
        withContext(IO) {
            if (userRepository.getCartProductById(productId)?.count == 1) {
                userRepository.deleteCartProductById(productId)
            } else {
                userRepository.decreaseCartProductCount(productId, count)
            }
        }

}