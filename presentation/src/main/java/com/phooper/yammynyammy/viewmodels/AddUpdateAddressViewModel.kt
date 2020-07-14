package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ph00.domain.models.AddressModel
import com.ph00.domain.usecases.AddAddressUseCase
import com.ph00.domain.usecases.DeleteAddressByUidUseCase
import com.ph00.domain.usecases.GetAddressByUidUseCase
import com.ph00.domain.usecases.UpdateAddressByUidUseCase
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.entities.Address
import com.phooper.yammynyammy.utils.Event
import com.phooper.yammynyammy.utils.toPresentation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
class AddUpdateAddressViewModel(
    private val addressUid: String?,
    private val getAddressByUidUseCase: GetAddressByUidUseCase,
    private val addAddressUseCase: AddAddressUseCase,
    private val deleteAddressByUidUseCase: DeleteAddressByUidUseCase,
    private val updateAddressByUidUseCase: UpdateAddressByUidUseCase
) : ViewModel() {

    private val _event = MutableLiveData<Event<ViewEvent>>()
    val event: LiveData<Event<ViewEvent>> get() = _event

    private val _state = MutableLiveData<ViewState>(ViewState.LOADING)
    val state: LiveData<ViewState> get() = _state

    private val _mode = MutableLiveData<ViewMode>()
    val mode: LiveData<ViewMode> get() = _mode

    private val _address = MutableLiveData<Address>()
    val address: LiveData<Address> get() = _address

    private val _inputValidator = MutableLiveData<InputValidator>()
    val inputValidator: LiveData<InputValidator> get() = _inputValidator

    init {
        checkBundle()
    }

    private fun checkBundle() {
        if (addressUid.isNullOrEmpty()) {
            _state.value = ViewState.DEFAULT
            _mode.value = ViewMode.NEW_ADDRESS
        } else {
            _mode.value = ViewMode.UPDATE_ADDRESS
            loadAddress()
        }
    }

    fun loadAddress() {
        getAddressByUidUseCase
            /**
             * Load address is available only in update address viewMode
             * so it is always not null
             * */
            .execute(addressUid!!)
            .onEach { addressModel ->
                _address.value = addressModel.toPresentation()
            }.onCompletion { _state.value = ViewState.DEFAULT }
            .catch { _state.value = ViewState.NETWORK_ERROR }
            .launchIn(viewModelScope)
    }

    fun addUpdateAddress(street: String, houseNum: String, apartNum: String) {
        if (street.isEmpty() || houseNum.isEmpty() || apartNum.isEmpty()) {
            _inputValidator.value = InputValidator(
                streetInputErrorResId = if (street.isEmpty()) R.string.fill_street else null,
                houseNumInputErrorResId = if (houseNum.isEmpty()) R.string.fill_house_num else null,
                apartNumInputErrorResId = if (apartNum.isEmpty()) R.string.fill_apart_num else null
            )
            return
        }

        if (addressUid == null) {
            addAddress(street, houseNum, apartNum)
        } else {
            updateAddress(street, houseNum, apartNum)
        }
    }

    private fun addAddress(street: String, houseNum: String, apartNum: String) {
        addAddressUseCase
            .execute(AddressModel(street = street, houseNum = houseNum, apartNum = apartNum))
            .onStart { _state.value = ViewState.LOADING }
            .onCompletion { _state.value = ViewState.DEFAULT }
            .onEach { _event.value = Event(ViewEvent.CREATE_SUCCESS) }
            .catch { _event.value = Event(ViewEvent.ERROR) }
            .launchIn(viewModelScope)
    }

    private fun updateAddress(street: String, houseNum: String, apartNum: String) {
        addressUid?.let {
            updateAddressByUidUseCase
                .execute(
                    it,
                    AddressModel(street = street, houseNum = houseNum, apartNum = apartNum)
                )
                .onStart { _state.value = ViewState.LOADING }
                .onCompletion { _state.value = ViewState.DEFAULT }
                .onEach { _event.value = Event(ViewEvent.UPDATE_SUCCESS) }
                .catch { _event.value = Event(ViewEvent.ERROR) }
                .launchIn(viewModelScope)
        }
    }

    fun deleteAddress() {
        addressUid?.let {
            deleteAddressByUidUseCase
                .execute(it)
                .onStart { _state.value = ViewState.LOADING }
                .onCompletion { _state.value = ViewState.DEFAULT }
                .onEach { _event.value = Event(ViewEvent.DELETE_SUCCESS) }
                .catch { _event.value = Event(ViewEvent.ERROR) }
                .launchIn(viewModelScope)
        }
    }

    enum class ViewState {
        LOADING,
        DEFAULT,
        NETWORK_ERROR
    }

    enum class ViewMode {
        NEW_ADDRESS,
        UPDATE_ADDRESS
    }

    enum class ViewEvent {
        ERROR,
        DELETE_SUCCESS,
        UPDATE_SUCCESS,
        CREATE_SUCCESS
    }

    data class InputValidator(
        val streetInputErrorResId: Int? = null,
        val houseNumInputErrorResId: Int? = null,
        val apartNumInputErrorResId: Int? = null
    )

}