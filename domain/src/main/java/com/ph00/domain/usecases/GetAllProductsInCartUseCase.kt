package com.ph00.domain.usecases

import com.ph00.domain.models.CartProductModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetAllProductsInCartUseCase@Inject constructor(
    private val getAllCartProductIdAndCountUseCase: GetAllCartProductIdAndCountUseCase,
    private val getProductListByIdsUseCase: GetProductListByIdsUseCase
) {

    fun execute(): Observable<List<CartProductModel>> =
        getAllCartProductIdAndCountUseCase.execute().flatMapSingle { prodAndCountList ->
            if (prodAndCountList.isNullOrEmpty()) {
                Single.just(emptyList())
            } else {
                getProductListByIdsUseCase.execute(ids = prodAndCountList.map { it.productId })
                    .map {
                        //Mapping to List<ProductInCart>
                        it.mapIndexed { index, product ->
                            CartProductModel(product, prodAndCountList[index].count)
                        }
                    }
            }
        }
}

