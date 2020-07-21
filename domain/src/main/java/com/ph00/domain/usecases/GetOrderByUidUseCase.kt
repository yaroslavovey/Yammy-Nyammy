package com.ph00.domain.usecases

import com.ph00.domain.models.OrderModel
import com.ph00.domain.repositories.UserRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetOrderByUidUseCase@Inject constructor(
    private val getCurrentUserUidUseCase: GetCurrentUserUidUseCase,
    private val userRepository: UserRepository
) {

    fun execute(orderUid: String): Single<OrderModel> =
        getCurrentUserUidUseCase.execute().flatMap { userUid ->
            userRepository.getOrderByUid(orderUid, userUid)
        }

}