package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.livermor.delegateadapter.delegate.diff.DiffUtilItem
import com.phooper.yammynyammy.data.models.TotalAndDeliveryPrice
import com.phooper.yammynyammy.domain.usecases.AddProductsToCartUseCase
import com.phooper.yammynyammy.domain.usecases.GetAllCartProductsUseCase
import com.phooper.yammynyammy.domain.usecases.RemoveProductsFromCartUseCase
import com.phooper.yammynyammy.utils.Constants
import kotlinx.coroutines.launch

class CartViewModel(
    private val getAllCartProductsUseCase: GetAllCartProductsUseCase,
    private val addProductsToCartUseCase: AddProductsToCartUseCase,
    private val removeProductsFromCartUseCase: RemoveProductsFromCartUseCase
) : ViewModel() {

    private val _state = MutableLiveData<ViewState>()
    val state: LiveData<ViewState> get() = _state

    val productsInCart: LiveData<List<DiffUtilItem>> get() = _productsInCart
    private val _productsInCart = MutableLiveData<List<DiffUtilItem>>()

    init {
        viewModelScope.launch {
            updateCart()
        }
    }

    private suspend fun updateCart() {
        _state.postValue(ViewState.LOADING)
        getAllCartProductsUseCase.execute()?.let { productsInCartList ->
            _productsInCart.postValue(productsInCartList.plus(
                TotalAndDeliveryPrice(
                    Constants.DELIVERY_PRICE,
                    productsInCartList.sumBy { it.totalPrice } + Constants.DELIVERY_PRICE)))
            _state.postValue(ViewState.DEFAULT)
            return
        }
        _state.postValue(ViewState.NO_PRODUCTS_IN_CART)
    }

    fun addOneProductToCart(productId: Int) {
        viewModelScope.launch {
            addProductsToCartUseCase.execute(productId, 1)
            updateCart()
        }
    }

    fun removeOneProductFromCart(productId: Int) {
        viewModelScope.launch {
            removeProductsFromCartUseCase.execute(productId, 1)
            updateCart()
        }
    }

    enum class ViewState {
        DEFAULT,
        LOADING,
        NO_PRODUCTS_IN_CART
    }
}