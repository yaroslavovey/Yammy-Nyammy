package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.ph00.domain.usecases.AddProductsToCartUseCase
import com.ph00.domain.usecases.GetProductByIdUseCase
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
        Transformations.switchMap(itemCount) { count ->
            Transformations.map(productPrice) { price ->
                return@map "${count * price.toInt()} ₽"
            }
        }

    private val _description = MutableLiveData<String>()
    val description: LiveData<String> get() = _description

    private val _imgLink = MutableLiveData<String>()
    val imgLink: LiveData<String> get() = _imgLink

    private val _productTitle = MutableLiveData<String>()
    val productTitle: LiveData<String> get() = _productTitle

    private val _productPrice = MutableLiveData<String>()
    private val productPrice: LiveData<String> get() = _productPrice

    init {
        viewModelScope.launch {
            loadProduct()
        }
    }

    private suspend fun loadProduct() {
        getProductByIdUseCase.execute(productId)?.let { product ->
            _imgLink.postValue(product.imageURL)
            _description.postValue(product.desc)
            _productPrice.postValue(product.price.toString())
            _productTitle.postValue(product.title)
            _state.postValue(ViewState.DEFAULT)
            return
        }
        delay(3000)
        loadProduct()
    }

    enum class ViewState {
        DEFAULT,
        LOADING
    }

}