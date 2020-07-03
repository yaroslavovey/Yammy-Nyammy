package com.phooper.yammynyammy.domain.usecases

import com.google.firebase.firestore.ktx.toObject
import com.phooper.yammynyammy.domain.models.Order
import com.phooper.yammynyammy.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class GetOrderByUidUseCase(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val userRepository: UserRepository
) {

    suspend fun execute(orderUid: String): Order? =
        withContext(IO) {
            getCurrentUserUseCase.execute()?.uid?.let { userUid ->
                userRepository.getOrderByUid(orderUid, userUid)?.toObject<Order>()
            }
        }
}