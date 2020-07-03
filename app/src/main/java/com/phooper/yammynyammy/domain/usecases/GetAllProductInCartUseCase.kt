package com.phooper.yammynyammy.domain.usecases

import com.phooper.yammynyammy.domain.models.CartProduct
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.withContext

class GetAllProductInCartUseCase(
    private val getAllCartProductIdAndCountUseCase: GetAllCartProductIdAndCountUseCase,
    private val getProductListByIdsUseCase: GetProductListByIdsUseCase
) {

    suspend fun execute(): List<CartProduct>? =
        withContext(Default) {
            getAllCartProductIdAndCountUseCase.execute()?.let { prodAndCountList ->
                if (prodAndCountList.isEmpty()) return@withContext null
                getProductListByIdsUseCase.execute(ids = prodAndCountList.map { it.productId })
                    //Mapping to List<ProductInCart>
                    ?.mapIndexed { index, product ->
                        CartProduct(
                            product,
                            prodAndCountList[index].count
                        )
                    }
            }
        }

}