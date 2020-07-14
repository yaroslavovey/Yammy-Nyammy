package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.livermor.delegateadapter.delegate.diff.KDiffUtilItem
import com.ph00.domain.usecases.GetOrderByUidUseCase
import com.phooper.yammynyammy.entities.TotalAndDeliveryPrice
import com.phooper.yammynyammy.utils.formatToString
import com.phooper.yammynyammy.utils.toPresentation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
class OrderViewModel(
    private val getOrderByUidUseCase: GetOrderByUidUseCase,
    private val orderUid: String?
) : ViewModel() {

    private val _state = MutableLiveData<ViewState>(ViewState.LOADING)
    val state: LiveData<ViewState> get() = _state

    private val _orderInfo = MutableLiveData<List<KDiffUtilItem>>()
    val orderInfo: LiveData<List<KDiffUtilItem>> get() = _orderInfo

    private val _appBarTitleDate = MutableLiveData<String>()
    val appBarTitleDate: LiveData<String> get() = _appBarTitleDate

    init {
        loadOrder()
    }

    fun loadOrder() {
        orderUid?.let {
            getOrderByUidUseCase
                .execute(it)
                .onStart { _state.value = ViewState.LOADING }
                .onCompletion { _state.value = ViewState.DEFAULT }
                .onEach { orderModel ->
                    with(orderModel) {
                        _appBarTitleDate.value = timestamp.formatToString()
                        _orderInfo.value = listOf<KDiffUtilItem>(addressAndStatus.toPresentation())
                            .plus(products.map { product -> product.toPresentation() }
                                .plus(TotalAndDeliveryPrice(deliveryPrice, totalPrice)))
                    }
                }
                .catch { _state.value = ViewState.NETWORK_ERROR }
                .launchIn(viewModelScope)
        }
    }

    enum class ViewState {
        LOADING,
        DEFAULT,
        NETWORK_ERROR
    }
}