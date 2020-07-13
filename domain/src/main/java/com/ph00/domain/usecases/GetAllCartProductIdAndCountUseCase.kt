package com.ph00.domain.usecases

import com.ph00.domain.models.ProductIdAndCountModel
import com.ph00.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow

class GetAllCartProductIdAndCountUseCase(private val userRepository: UserRepository) {

    fun execute(): Flow<List<ProductIdAndCountModel>?> =
        userRepository.getAllCartProducts()

}