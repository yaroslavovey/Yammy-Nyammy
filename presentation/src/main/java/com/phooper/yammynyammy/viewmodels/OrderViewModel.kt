package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.livermor.delegateadapter.delegate.diff.DiffUtilItem
import com.livermor.delegateadapter.delegate.diff.KDiffUtilItem
import com.ph00.domain.usecases.GetOrderByUidUseCase
import com.phooper.yammynyammy.entities.TotalAndDeliveryPrice
import com.phooper.yammynyammy.utils.formatToRussianString
import com.phooper.yammynyammy.utils.toPresentation
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
                    mutableListOf<KDiffUtilItem>().apply {
                        add(order.addressAndStatus.toPresentation())
                        addAll(order.products.map { it.toPresentation() })
                        add(
                            TotalAndDeliveryPrice(
                                order.deliveryPrice,
                                order.totalPrice
                            )
                        )
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