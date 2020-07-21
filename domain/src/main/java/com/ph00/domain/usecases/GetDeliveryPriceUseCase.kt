package com.ph00.domain.usecases

import com.ph00.domain.repositories.UserRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetDeliveryPriceUseCase@Inject constructor(private val userRepository: UserRepository) {

    fun execute(addressUid: String? = null): Single<Int> =
        userRepository.getDeliveryPrice(addressUid)

}