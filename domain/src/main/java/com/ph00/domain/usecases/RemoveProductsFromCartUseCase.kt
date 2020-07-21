package com.ph00.domain.usecases

import com.ph00.domain.repositories.UserRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class RemoveProductsFromCartUseCase@Inject constructor(private val userRepository: UserRepository) {

    fun execute(productId: Int, count: Int): Completable =
        userRepository.getCartProductById(productId).flatMapCompletable {
            if (it?.count == 1)
                userRepository.deleteCartProductById(productId)
            else
                userRepository.decreaseCartProductCount(productId, count)
        }

}