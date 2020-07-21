package com.ph00.domain.usecases

import com.ph00.domain.models.OrderModel
import com.ph00.domain.repositories.UserRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GetOrderListUseCase@Inject constructor(
    private val userRepository: UserRepository,
    private val getCurrentUserUidUseCase: GetCurrentUserUidUseCase
) {

    fun execute(): Observable<List<OrderModel>> =
        getCurrentUserUidUseCase.execute().flatMapObservable { userUid ->
            userRepository.getOrdersList(userUid)
        }

}