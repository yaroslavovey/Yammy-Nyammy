package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.*
import com.livermor.delegateadapter.delegate.diff.KDiffUtilItem
import com.ph00.domain.usecases.*
import com.phooper.yammynyammy.entities.TotalAndDeliveryPrice
import com.phooper.yammynyammy.utils.toPresentation
import kotlinx.coroutines.launch

class CartViewModel(
    private val getAllProductInCartLiveDataUseCase: GetAllProductInCartLiveDataUseCase,
    private val getAllCartProductIdAndCountLiveDataUseCase: GetAllCartProductIdAndCountLiveDataUseCase,
    private val addProductsToCartUseCase: AddProductsToCartUseCase,
    private val removeProductsFromCartUseCase: RemoveProductsFromCartUseCase,
    private val getDeliveryPriceUseCase: GetDeliveryPriceUseCase
) : ViewModel() {

    private val _state = MutableLiveData<ViewState>()
    val state: LiveData<ViewState> get() = _state

    val productsInCart = MediatorLiveData<List<KDiffUtilItem>>().apply {
        addSource(getAllProductInCartLiveDataUseCase.execute(viewModelScope)) { productsInCartList ->
            _state.postValue(
                if (productsInCartList.isNullOrEmpty()) {
                    ViewState.NO_PRODUCTS_IN_CART
                } else {
                    postValue(
                        productsInCartList.map { it.toPresentation() }
                            .plus(getDeliveryPriceUseCase.execute().let { deliveryPrice ->
                                TotalAndDeliveryPrice(
                                    deliveryPrice,
                                    productsInCartList.sumBy { it.totalPrice } + deliveryPrice)
                            }
                            )
                    )
                    ViewState.DEFAULT
                }
            )

        }
        addSource(getAllCartProductIdAndCountLiveDataUseCase.execute())
        {
            _state.postValue(ViewState.LOADING)
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