package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.livermor.delegateadapter.delegate.diff.DiffUtilItem
import com.phooper.yammynyammy.domain.models.TotalAndDeliveryPrice
import com.phooper.yammynyammy.domain.usecases.GetOrderByUidUseCase
import com.phooper.yammynyammy.utils.formatToRussianString
import kotlinx.coroutines.launch

class OrderViewModel(
    private val getOrderByUidUseCase: GetOrderByUidUseCase,
    private val orderUid: String?
) : ViewModel() {

    private val _state = MutableLiveData<ViewState>(ViewState.LOADING)
    val state: LiveData<ViewState> get() = _state

    private val _orderInfo = MutableLiveData<List<DiffUtilItem>>()
    val orderInfo: LiveData<List<DiffUtilItem>> get() = _orderInfo

    private val _appBarTitleDate = MutableLiveData<String>()
    val appBarTitleDate: LiveData<String> get() = _appBarTitleDate

    init {
        viewModelScope.launch {
            orderUid?.let { uid ->
                getOrderByUidUseCase.execute(uid)?.let { order ->
                    order.timestamp
                        ?.formatToRussianString()
                        .let { date -> _appBarTitleDate.postValue(date) }
                    mutableListOf<DiffUtilItem>().apply {
                        add(order.addressAndStatus)
                        addAll(order.products)
                        add(TotalAndDeliveryPrice(order.deliveryPrice, order.totalPrice))
                    }.let { _orderInfo.postValue(it) }
                }
            }
            _state.postValue(ViewState.DEFAULT)
        }
    }

    enum class ViewState {
        LOADING,
        DEFAULT
    }
}