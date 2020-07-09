package com.ph00.domain.usecases

import com.ph00.domain.models.CartProductModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllProductInCartAsFlowUseCase(
    private val getAllCartProductIdAndCountAsFlowUseCase: GetAllCartProductIdAndCountAsFLowUseCase,
    private val getProductListByIdsUseCase: GetProductListByIdsUseCase
) {

    fun execute(): Flow<List<CartProductModel>?> =
        getAllCartProductIdAndCountAsFlowUseCase.execute().map { prodAndCountList ->
            if (prodAndCountList.isNullOrEmpty()) return@map null
            getProductListByIdsUseCase.execute(ids = prodAndCountList.map { it.productId })
                //Mapping to List<ProductInCart>
                ?.mapIndexed { index, product ->
                    CartProductModel(
                        product,
                        prodAndCountList[index].count
                    )
                }
        }
}

