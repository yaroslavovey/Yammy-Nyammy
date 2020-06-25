package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phooper.yammynyammy.data.models.Product
import com.phooper.yammynyammy.data.repositories.ProductsRepository
import com.phooper.yammynyammy.utils.Event
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductListViewModel(
    private val category: Int?,
    private val productsRepository: ProductsRepository
) : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    private val _event = MutableLiveData<Event<ViewEvent>>()
    val event: LiveData<Event<ViewEvent>> get() = _event

    private val _state = MutableLiveData<ViewState>()
    val state: LiveData<ViewState> get() = _state

    init {
        viewModelScope.launch {
            loadProducts()
        }
    }

    private suspend fun loadProducts() {
        withContext(IO) {
            try {
                _products.postValue(productsRepository.getProductListByCategory(category.toString()))
                _state.postValue(ViewState.DEFAULT)
            } catch (e: Exception) {
                _event.postValue(Event(ViewEvent.ERROR))
                delay(5000)
                loadProducts()
            }
        }
    }

    enum class ViewEvent {
        ERROR
    }

    enum class ViewState {
        DEFAULT,
        LOADING
    }
}