package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.*
import com.ph00.domain.usecases.GetAddressByUidUseCase
import com.ph00.domain.usecases.GetAllCartProductCountUseCase
import com.ph00.domain.usecases.GetIsUserSignedInUseCase
import com.phooper.yammynyammy.utils.Event
import com.phooper.yammynyammy.utils.formatAddress
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@FlowPreview
@ExperimentalCoroutinesApi
class MainContainerViewModel(
    getAllCartProductCountUseCase: GetAllCartProductCountUseCase,
    getIsUserSignedInUseCase: GetIsUserSignedInUseCase,
    private val getAddressByUidUseCase: GetAddressByUidUseCase
) : ViewModel() {

    private val _selectedAddress = MutableLiveData<String>()
    val selectedAddress: LiveData<String> get() = _selectedAddress

    private val _event = MutableLiveData<Event<ViewEvent>>()
    val event: LiveData<Event<ViewEvent>> get() = _event

    val cartItemCount: LiveData<Int> =
        getAllCartProductCountUseCase.execute().asLiveData(IO)

    init {
        getIsUserSignedInUseCase
            .execute()
            .buffer()
            .onEach { if (!it) _event.value = Event(ViewEvent.NAVIGATE_TO_LOGIN_ACTIVITY) }
            .launchIn(viewModelScope)
    }

    fun selectAddress(uid: String) {
        getAddressByUidUseCase
            .execute(uid)
            .onEach { address ->
                _selectedAddress.value = formatAddress(
                    address.houseNum,
                    address.apartNum,
                    address.street
                )
            }
            .launchIn(viewModelScope)
    }

    fun resetAddress() {
        _selectedAddress.value = ""
    }

    fun triggerAddedToCartEvent() {
        _event.value = Event(ViewEvent.ADDED_TO_CART)
    }

    enum class ViewEvent {
        ADDED_TO_CART,
        NAVIGATE_TO_LOGIN_ACTIVITY
    }

}