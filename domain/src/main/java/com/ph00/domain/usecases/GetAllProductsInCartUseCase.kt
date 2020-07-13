package com.ph00.domain.usecases

import com.ph00.domain.models.CartProductModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

@FlowPreview
class GetAllProductsInCartUseCase(
    private val getAllCartProductIdAndCountUseCase: GetAllCartProductIdAndCountUseCase,
    private val getProductListByIdsUseCase: GetProductListByIdsUseCase
) {

    fun execute(): Flow<List<CartProductModel>> =
        getAllCartProductIdAndCountUseCase.execute().flatMapConcat { prodAndCountList ->
            if (prodAndCountList.isNullOrEmpty()) {
                flow { emit(emptyList()) }
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

