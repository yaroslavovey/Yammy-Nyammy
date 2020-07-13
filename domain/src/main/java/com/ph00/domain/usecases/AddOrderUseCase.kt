package com.ph00.domain.usecases

import com.ph00.domain.models.OrderAddressAndStatusModel
import com.ph00.domain.models.OrderModel
import com.ph00.domain.repositories.UserRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat

@FlowPreview
class AddOrderUseCase(
    private val userRepository: UserRepository,
    private val getCurrentUserUidUseCase: GetCurrentUserUidUseCase,
    private val getDeliveryPriceUseCase: GetDeliveryPriceUseCase,
    private val dropCartUseCase: DropCartUseCase,
    private val getAllProductsInCartUseCase: GetAllProductsInCartUseCase
) {

    fun execute(address: String): Flow<Unit> =
        getCurrentUserUidUseCase.execute().flatMapConcat { userUid ->
            getDeliveryPriceUseCase.execute().flatMapConcat { deliveryPrice ->
                getAllProductsInCartUseCase.execute().flatMapConcat { listCartProductModel ->
                    userRepository.addOrder(
                        OrderModel(
                            addressAndStatus = OrderAddressAndStatusModel(address = address),
                            products = listCartProductModel,
                            deliveryPrice = deliveryPrice
                        ), userUid
                    ).flatMapConcat {
                        dropCartUseCase.execute()
                    }
                }
            }
        }

}