package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ph00.domain.usecases.GetProductListByCategoryUseCase
import com.phooper.yammynyammy.entities.Product
import com.phooper.yammynyammy.utils.Event
import com.phooper.yammynyammy.utils.toPresentation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val category: Int?,
    private val getProductListByCategoryUseCase: GetProductListByCategoryUseCase
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
        getProductListByCategoryUseCase.execute(category.toString())?.let {list ->
            _products.postValue(list.map { it.toPresentation() })
            _state.postValue(ViewState.DEFAULT)
            return
        }
        _event.postValue(Event(ViewEvent.ERROR))
        delay(5000)
        loadProducts()
    }

    enum class ViewEvent {
        ERROR
    }

    enum class ViewState {
        DEFAULT,
        LOADING
    }
}