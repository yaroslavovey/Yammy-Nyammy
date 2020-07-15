package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.ph00.domain.usecases.AddProductsToCartUseCase
import com.ph00.domain.usecases.GetProductByIdUseCase
import com.phooper.yammynyammy.entities.Product
import com.phooper.yammynyammy.utils.toPresentation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
class ProductViewModel(
    private val productId: Int,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    addProductsToCartUseCase: AddProductsToCartUseCase
) :
    AddToCartDialogViewModel(
        productId = productId,
        addProductsToCartUseCase = addProductsToCartUseCase
    ) {

    private val _state = MutableLiveData<ViewState>()
    val state: LiveData<ViewState> get() = _state

    val totalPrice: LiveData<String> =
        Transformations.switchMap(_itemCount) { count ->
            Transformations.map(_product) { product ->
                return@map "${count * product.price}"
            }
        }

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> get() = _product

    init {
        loadProduct()
    }

    fun loadProduct() {
        getProductByIdUseCase
            .execute(productId)
            .onStart { _state.value = ViewState.LOADING }
            .onEach { _product.value = it.toPresentation() }
            .onCompletion { _state.value = ViewState.DEFAULT }
            .catch { _state.value = ViewState.NETWORK_ERROR }
            .launchIn(viewModelScope)
    }

    enum class ViewState {
        DEFAULT,
        LOADING,
        NETWORK_ERROR
    }

}