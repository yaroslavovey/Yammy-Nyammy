package com.phooper.yammynyammy.domain.usecases

import com.phooper.yammynyammy.data.models.ProductInCart
import com.phooper.yammynyammy.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.withContext

class GetAllCartProductsUseCase(
    private val userRepository: UserRepository,
    private val getProductListByIdsUseCase: GetProductListByIdsUseCase
) {

    suspend fun execute(): List<ProductInCart>? =
        withContext(Default) {
            userRepository.getAllCartProducts()?.let { prodAndCountList ->
                if (prodAndCountList.isEmpty()) return@withContext null
                getProductListByIdsUseCase.execute(ids = prodAndCountList.map { it.productId })
                    //Mapping to List<ProductInCart>
                    ?.mapIndexed { index, product ->
                        ProductInCart(
                            product,
                            prodAndCountList[index].count
                        )
                    }
            }
        }

}