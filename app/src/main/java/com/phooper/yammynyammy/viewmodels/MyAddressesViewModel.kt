package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObjects
import com.livermor.delegateadapter.delegate.diff.DiffUtilItem
import com.phooper.yammynyammy.domain.models.AddAddressButton
import com.phooper.yammynyammy.domain.models.Address
import com.phooper.yammynyammy.domain.usecases.GetAddressesAsCollectionUseCase
import kotlinx.coroutines.launch

class MyAddressesViewModel(
    private val getAddressesAsCollectionUseCase: GetAddressesAsCollectionUseCase,
    private val choosingAddressForDelivery: Boolean
) :
    ViewModel() {

    private val _state = MutableLiveData<ViewState>(ViewState.LOADING)
    val state: LiveData<ViewState> get() = _state

    private val _mode = MutableLiveData<ViewMode>()
    val mode: LiveData<ViewMode> get() = _mode

    val addressesList: LiveData<List<DiffUtilItem>> get() = _addressesList
    private val _addressesList = MutableLiveData<List<DiffUtilItem>>()

    init {
        viewModelScope.launch {
            if (choosingAddressForDelivery) {
                _mode.postValue(ViewMode.CHOOSING_DELIVERY_ADDRESS)
            } else {
                _mode.postValue(ViewMode.CHECKING_OUT_ADDRESSES)
            }
            getAddressesAsCollectionUseCase
                .execute()
                ?.addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
                    if (e != null) return@EventListener
                    //Doesn't work
                    _state.postValue(ViewState.LOADING)
                    //
                    value?.toObjects<Address>().let { listAddress ->
                        if (listAddress.isNullOrEmpty()) {
                            _state.postValue(ViewState.NO_ADDRESSES)
                        } else {
                            _addressesList.postValue(listAddress.plus(AddAddressButton()))
                            _state.postValue(ViewState.DEFAULT)
                        }
                    }
                })
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