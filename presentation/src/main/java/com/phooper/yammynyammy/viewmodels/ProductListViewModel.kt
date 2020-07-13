package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ph00.domain.usecases.GetProductListByCategoryUseCase
import com.phooper.yammynyammy.entities.Product
import com.phooper.yammynyammy.utils.Event
import com.phooper.yammynyammy.utils.cancelIfActive
import com.phooper.yammynyammy.utils.toPresentation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
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

    private var loadProductsJob: Job? = null

    init {
        loadProducts()
    }

    fun loadProducts() {
        loadProductsJob.cancelIfActive()
        loadProductsJob =
            getProductListByCategoryUseCase
                .execute(category.toString())
                .onStart { _state.value = ViewState.LOADING }
                .onCompletion { _state.value = ViewState.DEFAULT }
                .onEach { list -> _products.value = list.map { it.toPresentation() } }
                .catch { _state.value = ViewState.NETWORK_ERROR }
                .launchIn(viewModelScope)
    }

    enum class ViewEvent {
        ERROR
    }

    enum class ViewState {
        DEFAULT,
        LOADING,
        NETWORK_ERROR
    }
}