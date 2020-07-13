package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.livermor.delegateadapter.delegate.diff.KDiffUtilItem
import com.ph00.domain.models.CartProductModel
import com.ph00.domain.usecases.AddProductsToCartUseCase
import com.ph00.domain.usecases.GetAllProductsInCartUseCase
import com.ph00.domain.usecases.GetDeliveryPriceUseCase
import com.ph00.domain.usecases.RemoveProductsFromCartUseCase
import com.phooper.yammynyammy.entities.TotalAndDeliveryPrice
import com.phooper.yammynyammy.utils.cancelIfActive
import com.phooper.yammynyammy.utils.toPresentation
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.plus

@FlowPreview
@ExperimentalCoroutinesApi
class CartViewModel(
    private val getAllProductsInCartUseCase: GetAllProductsInCartUseCase,
    private val addProductsToCartUseCase: AddProductsToCartUseCase,
    private val removeProductsFromCartUseCase: RemoveProductsFromCartUseCase,
    private val getDeliveryPriceUseCase: GetDeliveryPriceUseCase
) : ViewModel() {

    private val _state = MutableLiveData<ViewState>()
    val state: LiveData<ViewState> get() = _state

    val productsInCart: LiveData<List<KDiffUtilItem>> get() = _productsInCart
    private val _productsInCart = MutableLiveData<List<KDiffUtilItem>>()

    private var cartProductModelListJob: Job? = null

    init {
        getCartProducts()
    }

    fun getCartProducts() {
        cartProductModelListJob.cancelIfActive()
        cartProductModelListJob =
            getAllProductsInCartUseCase
                .execute()
                .onEach {
                    processCartProductModelList(it)
                }
                .onStart { _state.postValue(ViewState.LOADING) }
                .onCompletion { _state.postValue(ViewState.DEFAULT) }
                .catch { _state.postValue(ViewState.NO_NETWORK) }
                .launchIn(viewModelScope + IO)
    }

    private fun processCartProductModelList(cartProductModelList: List<CartProductModel>) {
        when {
            cartProductModelList.isNullOrEmpty() -> {
                _state.postValue(ViewState.NO_PRODUCTS_IN_CART)
            }
            else -> {
                _productsInCart.postValue(prepareCartProductList(cartProductModelList))
                _state.postValue(ViewState.DEFAULT)
            }
        }
    }

    private fun prepareCartProductList(cartProductModelList: List<CartProductModel>): List<KDiffUtilItem> =
        (cartProductModelList.map { it.toPresentation() }
            .plus(getTotalAndDeliveryPrice(cartProductModelList)))

    //TODO GET DELIVERY PRICE
    private fun getTotalAndDeliveryPrice(cartProductModelList: List<CartProductModel>): TotalAndDeliveryPrice =
        TotalAndDeliveryPrice(
            10,
            cartProductModelList.sumBy { it.totalPrice } + 10)

    fun addOneProductToCart(productId: Int) {
        _state.value = ViewState.LOADING
        addProductsToCartUseCase
            .execute(productId, 1)
            .launchIn(viewModelScope + IO)
    }

    fun removeOneProductFromCart(productId: Int) {
        _state.value = ViewState.LOADING
        removeProductsFromCartUseCase
            .execute(productId, 1)
            .launchIn(viewModelScope + IO)
    }

    enum class ViewState {
        DEFAULT,
        LOADING,
        NO_PRODUCTS_IN_CART,
        NO_NETWORK
    }

}