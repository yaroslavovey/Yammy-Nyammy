package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.ph00.domain.usecases.AddProductsToCartUseCase
import com.ph00.domain.usecases.GetProductByIdUseCase
import com.phooper.yammynyammy.entities.Product
import com.phooper.yammynyammy.utils.toPresentation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
                return@map "${count * product.price} $"
            }
        }

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> get() = _product

    init {
        viewModelScope.launch {
            loadProduct()
        }
    }

    private suspend fun loadProduct() {
        getProductByIdUseCase.execute(productId)?.let { product ->
            _product.postValue(product.toPresentation())
            _state.postValue(ViewState.DEFAULT)
            return
        }
        delay(5000)
        loadProduct()
    }

    enum class ViewState {
        DEFAULT,
        LOADING
    }

}