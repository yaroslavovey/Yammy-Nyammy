package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phooper.yammynyammy.domain.models.Address
import com.phooper.yammynyammy.domain.usecases.AddAddressUseCase
import com.phooper.yammynyammy.domain.usecases.DeleteAddressByUidUseCase
import com.phooper.yammynyammy.domain.usecases.GetAddressByUidUseCase
import com.phooper.yammynyammy.domain.usecases.UpdateAddressByUidUseCase
import com.phooper.yammynyammy.utils.Event
import kotlinx.coroutines.launch

class AddUpdateAddressViewModel(
    private val addressUid: String?,
    private val getAddressByUidUseCase: GetAddressByUidUseCase,
    private val addAddressUseCase: AddAddressUseCase,
    private val deleteAddressByUidUseCase: DeleteAddressByUidUseCase,
    private val updateAddressByUidUseCase: UpdateAddressByUidUseCase
) : ViewModel() {

    private val _event = MutableLiveData<Event<ViewEvent>>()
    val event: LiveData<Event<ViewEvent>> get() = _event

    private val _state = MutableLiveData<ViewState>()
    val state: LiveData<ViewState> get() = _state

    private val _addressLiveData = MutableLiveData<Address>()
    val addressLiveData: LiveData<Address> get() = _addressLiveData

    init {
        viewModelScope.launch {
            checkBundle()
        }
    }

    private suspend fun checkBundle() {
        _state.postValue(ViewState.LOADING)
        addressUid?.let { uid ->
            getAddressByUidUseCase.execute(uid)?.let {
                _state.postValue(ViewState.DEFAULT_UPDATE_ADDRESS)
                _addressLiveData.postValue(it)
                return
            }
        }
        _state.postValue(ViewState.DEFAULT_NEW_ADDRESS)
    }

    fun addAddress(street: String, houseNum: String, apartNum: String) {
        viewModelScope.launch {
            _state.postValue(ViewState.LOADING)
            addAddressUseCase.execute(
                Address(
                    street = street,
                    houseNum = houseNum,
                    apartNum = apartNum
                )
            )?.let {
                _event.postValue(Event(ViewEvent.CREATE_SUCCESS))
                return@launch
            }
            _event.postValue(Event(ViewEvent.ERROR))
        }
    }

    fun deleteAddress() {
        viewModelScope.launch {
            _state.postValue(ViewState.LOADING)
            addressUid?.let { uid ->
                deleteAddressByUidUseCase.execute(uid)?.let {
                    _event.postValue(Event(ViewEvent.DELETE_SUCCESS))
                    return@launch
                }
                _event.postValue(Event(ViewEvent.ERROR))
            }
        }
    }

    fun updateAddress(street: String, houseNum: String, apartNum: String) {
        viewModelScope.launch {
            _state.postValue(ViewState.LOADING)
            addressUid?.let { uid ->
                updateAddressByUidUseCase.execute(
                    uid,
                    Address(
                        street = street,
                        houseNum = houseNum,
                        apartNum = apartNum
                    )
                )
            }?.let {
                _event.postValue(Event(ViewEvent.UPDATE_SUCCESS))
                return@launch
            }
            _event.postValue(Event(ViewEvent.ERROR))
        }
    }

    enum class ViewState {
        LOADING,
        DEFAULT_NEW_ADDRESS,
        DEFAULT_UPDATE_ADDRESS
    }

    enum class ViewEvent {
        ERROR,
        DELETE_SUCCESS,
        UPDATE_SUCCESS,
        CREATE_SUCCESS
    }
}