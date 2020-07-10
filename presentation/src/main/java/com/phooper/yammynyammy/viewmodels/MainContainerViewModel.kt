package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.*
import com.ph00.domain.usecases.GetAddressByUidUseCase
import com.ph00.domain.usecases.GetAllCartProductIdAndCountAsFLowUseCase
import com.ph00.domain.usecases.GetCurrentUserUidUseCase
import com.ph00.domain.usecases.GetUserDataUseCase
import com.phooper.yammynyammy.utils.Event
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainContainerViewModel(
    private val getAllCartProductIdAndCountAsFLowUseCase: GetAllCartProductIdAndCountAsFLowUseCase,
    private val getAddressByUidUseCase: GetAddressByUidUseCase,
    private val getCurrentUserUidUseCase: GetCurrentUserUidUseCase,
    private val getUserDataUseCase: GetUserDataUseCase
) : ViewModel() {

    private val _event = MutableLiveData<Event<ViewEvent>>()
    val event: LiveData<Event<ViewEvent>> get() = _event

    private val _selectedAddress = MutableLiveData<String>()
    val selectedAddress: LiveData<String> get() = _selectedAddress

    val cartItemCount: LiveData<Int> =
        getAllCartProductIdAndCountAsFLowUseCase.execute().asLiveData(IO)
            .map { list -> if (list.isNullOrEmpty()) 0 else list.sumBy { it.count } }

    init {
        checkUser()
    }

    private fun checkUser() {
        viewModelScope.launch {
            if (getCurrentUserUidUseCase.execute() == null || getUserDataUseCase.execute() == null)
                _event.postValue(Event(ViewEvent.NAVIGATE_TO_LOGIN_ACTIVITY))
        }
    }

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