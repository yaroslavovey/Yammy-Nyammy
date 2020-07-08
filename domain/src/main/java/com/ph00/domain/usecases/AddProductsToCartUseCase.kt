package com.ph00.domain.usecases

import com.ph00.domain.models.ProductIdAndCountModel
import com.ph00.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class AddProductsToCartUseCase(private val userRepository: UserRepository) {

    suspend fun execute(productId: Int, count: Int) =
        withContext(IO) {
            if (userRepository.getCartProductById(productId) == null) {
                userRepository.addCartProduct(
                    ProductIdAndCountModel(
                        productId,
                        count
                    )
                )
            } else {
                userRepository.increaseCartProductCount(productId, count)
            }
        }

}