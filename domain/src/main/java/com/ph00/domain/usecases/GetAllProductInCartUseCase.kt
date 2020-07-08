package com.ph00.domain.usecases

import com.ph00.domain.models.CartProductModel
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.withContext

class GetAllProductInCartUseCase(
    private val getAllCartProductIdAndCountUseCase: GetAllCartProductIdAndCountUseCase,
    private val getProductListByIdsUseCase: GetProductListByIdsUseCase
) {

    suspend fun execute(): List<CartProductModel>? =
        withContext(Default) {
            getAllCartProductIdAndCountUseCase.execute()?.let { prodAndCountList ->
                if (prodAndCountList.isEmpty()) return@withContext null
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

}