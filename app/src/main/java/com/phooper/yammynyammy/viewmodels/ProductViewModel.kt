package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.phooper.yammynyammy.data.repositories.ProductsRepository
import com.phooper.yammynyammy.data.repositories.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductViewModel(
    userRepository: UserRepository,
    private val productsRepository: ProductsRepository,
    private val productId: Int
) :
    AddToCartDialogViewModel(productId = productId, userRepository = userRepository) {

    private val _state = MutableLiveData<ViewState>()
    val state: LiveData<ViewState> get() = _state

    val totalPrice =
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
        withContext(IO) {
            try {
                productsRepository.getProductById(productId).let { product ->
                    _imgLink.postValue(product.imageURL)
                    _description.postValue(product.desc)
                    _productPrice.postValue(product.price)
                    _productTitle.postValue(product.title)
                }
                _state.postValue(ViewState.DEFAULT)
            } catch (e: Exception) {
                delay(3000)
                loadProduct()
            }
        }
    }

    enum class ViewState {
        DEFAULT,
        LOADING
    }

}