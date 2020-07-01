package com.phooper.yammynyammy.domain.usecases

import com.phooper.yammynyammy.data.models.ProductIdAndCount
import com.phooper.yammynyammy.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class AddProductsToCartUseCase(private val userRepository: UserRepository) {

    suspend fun execute(productId: Int, count: Int) =
        withContext(IO) {
            if (userRepository.getCartProductById(productId) == null) {
                userRepository.addCartProduct(ProductIdAndCount(productId, count))
            } else {
                userRepository.increaseCartProductCount(productId, count)
            }
        }

}