package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.*
import com.ph00.domain.usecases.AddProductsToCartUseCase
import com.ph00.domain.usecases.GetAllProductInCartAsFlowUseCase
import com.ph00.domain.usecases.GetDeliveryPriceUseCase
import com.ph00.domain.usecases.RemoveProductsFromCartUseCase
import com.phooper.yammynyammy.entities.TotalAndDeliveryPrice
import com.phooper.yammynyammy.utils.toPresentation
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class CartViewModel(
    getAllProductInCartAsFlowUseCase: GetAllProductInCartAsFlowUseCase,
    private val addProductsToCartUseCase: AddProductsToCartUseCase,
    private val removeProductsFromCartUseCase: RemoveProductsFromCartUseCase,
    private val getDeliveryPriceUseCase: GetDeliveryPriceUseCase
) : ViewModel() {

    private val _state = MutableLiveData<ViewState>(ViewState.LOADING)
    val state: LiveData<ViewState> get() = _state

    val productsInCart =
        getAllProductInCartAsFlowUseCase
            .execute()
            .asLiveData(Default)
            .switchMap { productsInCartList ->
                liveData(context = viewModelScope.coroutineContext + IO) {
                    _state.postValue(
                        if (productsInCartList.isNullOrEmpty()) {
                            ViewState.NO_PRODUCTS_IN_CART
                        } else {
                            getDeliveryPriceUseCase.execute().let { deliveryPrice ->
                                emit(
                                    productsInCartList.map { it.toPresentation() }
                                        .plus(
                                            TotalAndDeliveryPrice(
                                                deliveryPrice,
                                                productsInCartList.sumBy { it.totalPrice } + deliveryPrice)
                                        )
                                )
                                ViewState.DEFAULT
                            }
                        }
                    )
                }
            }

    fun addOneProductToCart(productId: Int) {
        viewModelScope.launch {
            addProductsToCartUseCase.execute(productId, 1)
        }
    }

    fun removeOneProductFromCart(productId: Int) {
        viewModelScope.launch {
            removeProductsFromCartUseCase.execute(productId, 1)
        }
    }

    enum class ViewState {
        DEFAULT,
        LOADING,
        NO_PRODUCTS_IN_CART
    }
}