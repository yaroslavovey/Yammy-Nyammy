package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.*
import com.livermor.delegateadapter.delegate.diff.DiffUtilItem
import com.phooper.yammynyammy.data.models.TotalAndDeliveryPrice
import com.phooper.yammynyammy.domain.usecases.AddProductsToCartUseCase
import com.phooper.yammynyammy.domain.usecases.GetAllCartProductIdAndCountLiveDataUseCase
import com.phooper.yammynyammy.domain.usecases.GetAllProductInCartLiveDataUseCase
import com.phooper.yammynyammy.domain.usecases.RemoveProductsFromCartUseCase
import com.phooper.yammynyammy.utils.Constants
import kotlinx.coroutines.launch

class CartViewModel(
    private val getAllProductInCartLiveDataUseCase: GetAllProductInCartLiveDataUseCase,
    private val getAllCartProductIdAndCountLiveDataUseCase: GetAllCartProductIdAndCountLiveDataUseCase,
    private val addProductsToCartUseCase: AddProductsToCartUseCase,
    private val removeProductsFromCartUseCase: RemoveProductsFromCartUseCase
) : ViewModel() {

    private val _state = MutableLiveData<ViewState>()
    val state: LiveData<ViewState> get() = _state

    val productsInCart = MediatorLiveData<List<DiffUtilItem>>().apply {
        addSource(getAllProductInCartLiveDataUseCase.execute(viewModelScope)) { productsInCartList ->
            _state.postValue(
                if (productsInCartList.isNullOrEmpty()) {
                    ViewState.NO_PRODUCTS_IN_CART
                } else {
                    postValue(
                        productsInCartList.plus(
                            TotalAndDeliveryPrice(
                                Constants.DELIVERY_PRICE,
                                productsInCartList.sumBy { it.totalPrice } + Constants.DELIVERY_PRICE)
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