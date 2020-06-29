package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.toObject
import com.phooper.yammynyammy.data.models.Address
import com.phooper.yammynyammy.data.repositories.UserRepository
import com.phooper.yammynyammy.utils.Event
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddUpdateAddressViewModel(
    private val userRepository: UserRepository,
    private val addressUid: String?
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
        withContext(IO) {
            _state.postValue(ViewState.LOADING)
            addressUid?.let { uid ->
                userRepository.getAddressByUid(uid)?.toObject<Address>()?.let {
                    _state.postValue(ViewState.DEFAULT_UPDATE_ADDRESS)
                    _addressLiveData.postValue(it)
                    return@withContext
                }
            }
            _state.postValue(ViewState.DEFAULT_NEW_ADDRESS)
        }
    }

    fun addAddress(street: String, houseNum: String, apartNum: String) {
        viewModelScope.launch(IO) {
            _state.postValue(ViewState.LOADING)
            userRepository.addUserAddress(
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
        viewModelScope.launch(IO) {
            _state.postValue(ViewState.LOADING)
            addressUid?.let { uid ->
                userRepository.deleteAddressByUid(uid)?.let {
                    _event.postValue(Event(ViewEvent.DELETE_SUCCESS))
                    return@launch
                }
                _event.postValue(Event(ViewEvent.ERROR))
            }
        }
    }

    fun updateAddress(street: String, houseNum: String, apartNum: String) {
        viewModelScope.launch(IO) {
            _state.postValue(ViewState.LOADING)
            addressUid?.let {uid ->
                userRepository.updateAddress(
                    Address(
                        uid = uid,
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