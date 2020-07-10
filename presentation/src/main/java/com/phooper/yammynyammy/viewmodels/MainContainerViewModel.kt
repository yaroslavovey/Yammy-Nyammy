package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.*
import com.ph00.domain.usecases.GetAddressByUidUseCase
import com.ph00.domain.usecases.GetAllCartProductIdAndCountAsFLowUseCase
import com.ph00.domain.usecases.GetIsUserSignedInFlow
import com.phooper.yammynyammy.utils.Event
import com.phooper.yammynyammy.utils.formatAddress
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainContainerViewModel(
    getAllCartProductIdAndCountAsFLowUseCase: GetAllCartProductIdAndCountAsFLowUseCase,
    getIsUserSignedInFlow: GetIsUserSignedInFlow,
    private val getAddressByUidUseCase: GetAddressByUidUseCase
) : ViewModel() {

    private val _selectedAddress = MutableLiveData<String>()
    val selectedAddress: LiveData<String> get() = _selectedAddress

    private val _event = MutableLiveData<Event<ViewEvent>>()
    val event: LiveData<Event<ViewEvent>> get() = _event

    val cartItemCount: LiveData<Int> =
        getAllCartProductIdAndCountAsFLowUseCase.execute().asLiveData(IO)
            .map { list -> if (list.isNullOrEmpty()) 0 else list.sumBy { it.count } }

    val userIsSignedIn: LiveData<Boolean> = getIsUserSignedInFlow.execute().asLiveData(IO)

    fun selectAddress(uid: String) {
        viewModelScope.launch {
            getAddressByUidUseCase.execute(uid)?.let { address ->
                _selectedAddress.postValue(
                    formatAddress(
                        address.houseNum,
                        address.apartNum,
                        address.street
                    )
                )
            }
        }
    }

    fun resetAddress() {
        _selectedAddress.value = ""
    }

    fun triggerAddedToCartEvent() {
        _event.value = Event(ViewEvent.ADDED_TO_CART)
    }

    enum class ViewEvent {
        ADDED_TO_CART
    }
}