package com.ph00.domain.usecases

import com.google.firebase.firestore.ktx.toObject
import com.ph00.domain.models.OrderModel
import com.ph00.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class GetOrderByUidUseCase(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val userRepository: UserRepository
) {

    suspend fun execute(orderUid: String): OrderModel? =
        withContext(IO) {
            getCurrentUserUseCase.execute()?.uid?.let { userUid ->
                userRepository.getOrderByUid(orderUid, userUid)?.toObject<OrderModel>()
            }
        }
}