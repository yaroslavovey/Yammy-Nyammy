package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phooper.yammynyammy.domain.usecases.GetAddressByUidUseCase
import com.phooper.yammynyammy.domain.usecases.GetAllCartProductsCountLiveDataUseCase
import com.phooper.yammynyammy.domain.usecases.GetCurrentUserUseCase
import com.phooper.yammynyammy.utils.Event
import kotlinx.coroutines.launch

class MainContainerViewModel(
    private val getAllCartProductsCountLiveDataUseCase: GetAllCartProductsCountLiveDataUseCase,
    private val getAddressByUidUseCase: GetAddressByUidUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _event = MutableLiveData<Event<ViewEvent>>()
    val event: LiveData<Event<ViewEvent>> get() = _event

    private val _selectedAddress = MutableLiveData<String>()
    val selectedAddress: LiveData<String> get() = _selectedAddress

    init {
        checkUser()
    }

    fun checkUser() {
        viewModelScope.launch {
            if (getCurrentUserUseCase.execute() == null)
                _event.postValue(Event(ViewEvent.NAVIGATE_TO_LOGIN_ACTIVITY))
        }
    }

    val cartItemCount: LiveData<Int> =
        getAllCartProductsCountLiveDataUseCase.execute(viewModelScope)

    fun selectAddress(uid: String) {
        viewModelScope.launch {
            getAddressByUidUseCase.execute(uid)?.let { address ->
                _selectedAddress.postValue("ул. ${address.street}, д. ${address.houseNum} кв. ${address.apartNum}")
            }
        }
    }

    fun resetAddress() {
        _selectedAddress.value = ""
    }

    enum class ViewEvent {
        NAVIGATE_TO_LOGIN_ACTIVITY
    }
}