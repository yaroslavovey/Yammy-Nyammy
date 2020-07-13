package com.ph00.domain.usecases

import com.ph00.domain.models.OrderModel
import com.ph00.domain.repositories.UserRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat

@FlowPreview
class GetOrderByUidUseCase(
    private val getCurrentUserUidUseCase: GetCurrentUserUidUseCase,
    private val userRepository: UserRepository
) {

    fun execute(orderUid: String): Flow<OrderModel> =
        getCurrentUserUidUseCase.execute().flatMapConcat { userUid ->
            userRepository.getOrderByUid(orderUid, userUid)
        }

}