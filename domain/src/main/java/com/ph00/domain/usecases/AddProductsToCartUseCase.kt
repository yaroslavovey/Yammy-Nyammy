package com.ph00.domain.usecases

import com.ph00.domain.models.ProductIdAndCountModel
import com.ph00.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.withContext

@FlowPreview
class AddProductsToCartUseCase(private val userRepository: UserRepository) {

    fun execute(productId: Int, count: Int): Flow<Unit> =
        userRepository.getCartProductById(productId).flatMapConcat {
            if (it == null){
                userRepository.addCartProduct(
                    ProductIdAndCountModel(productId, count))
            } else {
                userRepository.increaseCartProductCount(productId, count)
            }
        }

}