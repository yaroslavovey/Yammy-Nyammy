package com.ph00.domain.usecases

import com.ph00.domain.models.ProductIdAndCountModel
import com.ph00.domain.repositories.UserRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GetAllCartProductIdAndCountUseCase@Inject constructor(private val userRepository: UserRepository) {

    fun execute(): Observable<List<ProductIdAndCountModel>?> =
        userRepository.getAllCartProducts()

}