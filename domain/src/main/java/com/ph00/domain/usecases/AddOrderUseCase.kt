package com.ph00.domain.usecases

import com.ph00.domain.models.OrderAddressAndStatusModel
import com.ph00.domain.models.OrderModel
import com.ph00.domain.repositories.UserRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class AddOrderUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val getCurrentUserUidUseCase: GetCurrentUserUidUseCase,
    private val getDeliveryPriceUseCase: GetDeliveryPriceUseCase,
    private val dropCartUseCase: DropCartUseCase,
    private val getAllProductsInCartUseCase: GetAllProductsInCartUseCase
) {

//    fun execute(address: String) : Completable =
//        Single.zip(
//            getCurrentUserUidUseCase.execute(),
//            getDeliveryPriceUseCase.execute(),
//            getAllProductsInCartUseCase.execute().singleOrError(),
//            Function3<String, Int, List<CartProductModel>, Completable> { userUid, deliveryPrice, listCartProductModel ->
//                userRepository.addOrder(
//                    OrderModel(
//                        addressAndStatus = OrderAddressAndStatusModel(address = address),
//                        products = listCartProductModel,
//                        deliveryPrice = deliveryPrice
//                    ), userUid
//                ).andThen { dropCartUseCase.execute() }
//            }
//        )

    //TODO Find better solution
    fun execute(address: String): Completable =
        getCurrentUserUidUseCase.execute().flatMapCompletable { userUid ->
            getDeliveryPriceUseCase.execute().flatMapCompletable { deliveryPrice ->
                getAllProductsInCartUseCase.execute().take(1)
                    .flatMapCompletable { listCartProductModel ->
                        userRepository.addOrder(
                            OrderModel(
                                addressAndStatus = OrderAddressAndStatusModel(address = address),
                                products = listCartProductModel,
                                deliveryPrice = deliveryPrice
                            ), userUid
                        ).andThen { dropCartUseCase.execute() }
                    }
            }
        }
}
