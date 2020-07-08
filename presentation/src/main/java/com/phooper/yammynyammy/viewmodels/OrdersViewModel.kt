package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ph00.domain.usecases.GetOrderListUseCase
import com.phooper.yammynyammy.entities.Order
import com.phooper.yammynyammy.utils.toPresentation
import kotlinx.coroutines.launch

class OrdersViewModel(private val getOrderListUseCase: GetOrderListUseCase) : ViewModel() {

    private val _state = MutableLiveData<ViewState>()
    val state: LiveData<ViewState> get() = _state

    private val _orderList = MutableLiveData<List<Order>>()
    val orderList: LiveData<List<Order>> get() = _orderList

    init {
        viewModelScope.launch { loadOrderList() }
    }

    private suspend fun loadOrderList() {
        _state.postValue(ViewState.LOADING)
        getOrderListUseCase.execute()?.let { list ->
            _orderList.postValue(list.map { it.toPresentation() })
            _state.postValue(ViewState.DEFAULT)
            return
        }
        _state.postValue(ViewState.NO_ORDERS)
    }

    enum class ViewState {
        DEFAULT,
        LOADING,
        NO_ORDERS
    }
}