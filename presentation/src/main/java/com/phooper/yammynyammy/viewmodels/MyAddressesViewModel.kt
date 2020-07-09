package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.*
import com.livermor.delegateadapter.delegate.diff.KDiffUtilItem
import com.ph00.domain.usecases.GetAllAddressesAsFlowUseCase
import com.phooper.yammynyammy.entities.AddAddressButton
import com.phooper.yammynyammy.utils.toPresentation
import kotlinx.coroutines.Dispatchers.IO

class MyAddressesViewModel(
    private val getAllUserAddressesAsFlowUseCase: GetAllAddressesAsFlowUseCase,
    private val choosingAddressForDelivery: Boolean
) : ViewModel() {

    private val _state = MutableLiveData<ViewState>(ViewState.LOADING)
    val state: LiveData<ViewState> get() = _state

    private val _mode = MutableLiveData<ViewMode>()
    val mode: LiveData<ViewMode> get() = _mode

    val addressesList: LiveData<List<KDiffUtilItem>>? =
        getAllUserAddressesAsFlowUseCase
            .execute()
            ?.asLiveData(IO)
            ?.switchMap { listAddress ->
                liveData(context = viewModelScope.coroutineContext + IO) {
                    _state.postValue(
                        if (listAddress.isNullOrEmpty()) {
                            ViewState.NO_ADDRESSES
                        } else {
                            emit(listAddress.map { it.toPresentation() }.plus(AddAddressButton()))
                            ViewState.DEFAULT
                        }
                    )
                }
            }

    init {
        setViewMode()
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
        NO_ADDRESSES
    }
}