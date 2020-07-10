package com.ph00.domain.usecases

import com.ph00.domain.models.OrderModel
import com.ph00.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class AddOrderUseCase(
    private val userRepository: UserRepository,
    private val getCurrentUserUidUseCase: GetCurrentUserUidUseCase
) {

    suspend fun execute(order: OrderModel): Boolean? =
        withContext(IO) {
            getCurrentUserUidUseCase.execute()?.let { userUid ->
                userRepository.addOrder(order, userUid)
            }
        }

}