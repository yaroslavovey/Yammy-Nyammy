package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.*
import com.phooper.yammynyammy.data.models.ProductInCart
import com.phooper.yammynyammy.data.repositories.ProductsRepository
import com.phooper.yammynyammy.data.repositories.UserRepository
import kotlinx.coroutines.Dispatchers.Default

class CartViewModel(
    userRepository: UserRepository,
    private val productsRepository: ProductsRepository
) : ViewModel() {

    val productsInCart: LiveData<List<ProductInCart>> =
        userRepository.getAllCartProducts().switchMap { prodAndCountList ->
            liveData(context = viewModelScope.coroutineContext + Default) {
                emit(productsRepository.getProductListByIds(ids = prodAndCountList
                    .map { productIdAndCount -> productIdAndCount.productId })
                    .mapIndexed { index, product ->
                        ProductInCart(
                            product,
                            prodAndCountList[index].count
                        )
                    })
            }
        }

}