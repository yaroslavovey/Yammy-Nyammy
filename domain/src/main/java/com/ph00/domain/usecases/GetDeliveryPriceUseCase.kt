package com.ph00.domain.usecases

import com.ph00.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class GetDeliveryPriceUseCase(private val userRepository: UserRepository) {

    fun execute(addressUid: String? = null): Int =
            userRepository.getDeliveryPrice(addressUid)

}