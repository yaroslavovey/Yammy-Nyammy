package com.ph00.domain.usecases

import com.ph00.domain.models.OrderModel
import com.ph00.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class GetOrderByUidUseCase(
    private val getCurrentUserUidUseCase: GetCurrentUserUidUseCase,
    private val userRepository: UserRepository
) {

    suspend fun execute(orderUid: String): OrderModel? =
        withContext(IO) {
            getCurrentUserUidUseCase.execute()?.let { userUid ->
                userRepository.getOrderByUid(orderUid, userUid)
            }
        }
}