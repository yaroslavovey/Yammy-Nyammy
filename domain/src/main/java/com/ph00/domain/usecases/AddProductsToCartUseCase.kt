package com.ph00.domain.usecases

import com.ph00.domain.models.ProductIdAndCountModel
import com.ph00.domain.repositories.UserRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class AddProductsToCartUseCase @Inject constructor(private val userRepository: UserRepository) {

    fun execute(productId: Int, count: Int): Completable =
        userRepository.getCartProductById(productId).flatMapCompletable {
            if (it == null) {
                userRepository.addCartProduct(ProductIdAndCountModel(productId, count))
            } else {
                userRepository.increaseCartProductCount(productId, count)
            }
        }

}