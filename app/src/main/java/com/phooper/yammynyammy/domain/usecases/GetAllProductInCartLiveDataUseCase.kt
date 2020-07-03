package com.phooper.yammynyammy.domain.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.phooper.yammynyammy.domain.models.CartProduct
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default

class GetAllProductInCartLiveDataUseCase(
    private val getAllCartProductIdAndCountLiveDataUseCase: GetAllCartProductIdAndCountLiveDataUseCase,
    private val getProductListByIdsUseCase: GetProductListByIdsUseCase
) {
    fun execute(coroutineScope: CoroutineScope): LiveData<List<CartProduct>> =
        getAllCartProductIdAndCountLiveDataUseCase.execute().switchMap { listProductIdAndCount ->
            liveData(coroutineScope.coroutineContext + Default) {
                if (listProductIdAndCount.isNullOrEmpty()) emit(emptyList()) else {
                    listProductIdAndCount.map { it.productId }.let { listProductIds ->
                        getProductListByIdsUseCase.execute(listProductIds)?.let { listProduct ->
                            listProduct.mapIndexed { index, product ->
                                CartProduct(product, listProductIdAndCount[index].count)
                            }.let { listProductInCart -> emit(listProductInCart) }
                        }
                    }
                }
            }
        }
}