package com.ph00.domain.usecases

import com.ph00.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow

class GetDeliveryPriceUseCase(private val userRepository: UserRepository) {

    fun execute(addressUid: String? = null): Flow<Int> =
        userRepository.getDeliveryPrice(addressUid)

}