package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ph00.domain.usecases.GetOrderListUseCase
import com.phooper.yammynyammy.entities.Order
import com.phooper.yammynyammy.utils.toPresentation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

@FlowPreview
@ExperimentalCoroutinesApi
class OrdersViewModel(private val getOrderListUseCase: GetOrderListUseCase) : ViewModel() {

    private val _state = MutableLiveData<ViewState>()
    val state: LiveData<ViewState> get() = _state

    private val _orderList = MutableLiveData<List<Order>>()
    val orderList: LiveData<List<Order>> get() = _orderList

    init {
        loadOrderList()
    }

    fun loadOrderList() {
        getOrderListUseCase
            .execute()
            .onStart { _state.value = ViewState.LOADING }
            .onEach { list ->
                if (list.isEmpty()) {
                    _state.value = ViewState.NO_ORDERS
                } else {
                    _orderList.value = list.map { it.toPresentation() }
                    _state.value = ViewState.DEFAULT
                }
            }
            .catch { _state.value = ViewState.NETWORK_ERROR }
            .launchIn(viewModelScope)
    }

    enum class ViewState {
        DEFAULT,
        LOADING,
        NO_ORDERS,
        NETWORK_ERROR
    }
}