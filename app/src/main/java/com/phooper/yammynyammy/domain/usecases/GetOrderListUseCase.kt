package com.phooper.yammynyammy.domain.usecases

import com.google.firebase.firestore.ktx.toObject
import com.phooper.yammynyammy.domain.models.Order
import com.phooper.yammynyammy.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class GetOrderListUseCase(
    private val userRepository: UserRepository,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) {

    suspend fun execute(): List<Order>? =
        withContext(IO) {
            getCurrentUserUseCase.execute()?.uid?.let { userUid ->
                userRepository.getOrdersList(userUid)?.mapNotNull { it.toObject<Order>() }.let {
                    if (it.isNullOrEmpty()) {
                        null
                    } else {
                        it
                    }
                }
            }
        }
}