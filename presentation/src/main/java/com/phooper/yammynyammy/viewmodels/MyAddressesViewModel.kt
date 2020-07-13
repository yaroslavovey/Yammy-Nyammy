package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.livermor.delegateadapter.delegate.diff.KDiffUtilItem
import com.ph00.domain.usecases.GetAllAddressesUseCase
import com.phooper.yammynyammy.entities.AddAddressButton
import com.phooper.yammynyammy.utils.toPresentation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

@FlowPreview
@ExperimentalCoroutinesApi
class MyAddressesViewModel(
    private val getAllUserAddressesUseCase: GetAllAddressesUseCase,
    private val choosingAddressForDelivery: Boolean
) : ViewModel() {

    private val _state = MutableLiveData<ViewState>(ViewState.LOADING)
    val state: LiveData<ViewState> get() = _state

    private val _mode = MutableLiveData<ViewMode>()
    val mode: LiveData<ViewMode> get() = _mode

    val addressesList: LiveData<List<KDiffUtilItem>> get() = _addressesList
    private val _addressesList = MutableLiveData<List<KDiffUtilItem>>()

    init {
        setViewMode()
        loadAddresses()
    }

    private fun loadAddresses() {
        getAllUserAddressesUseCase
            .execute()
            .onStart { _state.value = ViewState.LOADING }
            .onEach { list ->
                if (list.isNullOrEmpty()) {
                    _state.value = ViewState.NO_ADDRESSES
                } else {
                    _addressesList.value = list.map { it.toPresentation() }.plus(AddAddressButton())
                    _state.value = ViewState.DEFAULT
                }
            }
            .catch { _state.value = ViewState.NETWORK_ERROR }
            .launchIn(viewModelScope)
    }

    private fun setViewMode() {
        if (choosingAddressForDelivery) {
            _mode.value = ViewMode.CHOOSING_DELIVERY_ADDRESS
        } else {
            _mode.value = ViewMode.CHECKING_OUT_ADDRESSES
        }
    }

    enum class ViewMode {
        CHOOSING_DELIVERY_ADDRESS,
        CHECKING_OUT_ADDRESSES
    }

    enum class ViewState {
        DEFAULT,
        LOADING,
        NO_ADDRESSES,
        NETWORK_ERROR
    }
}