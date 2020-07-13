package com.ph00.domain.usecases

import com.ph00.domain.repositories.UserRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flatMapConcat

@FlowPreview
class GetOrderListUseCase(
    private val userRepository: UserRepository,
    private val getCurrentUserUidUseCase: GetCurrentUserUidUseCase
) {

    fun execute() =
        getCurrentUserUidUseCase.execute().flatMapConcat { userUid ->
            userRepository.getOrdersList(userUid)
        }

}