package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObjects
import com.livermor.delegateadapter.delegate.diff.DiffUtilItem
import com.phooper.yammynyammy.data.models.AddAddressButton
import com.phooper.yammynyammy.data.models.Address
import com.phooper.yammynyammy.domain.usecases.GetAddressesAsCollectionUseCase
import kotlinx.coroutines.launch

class MyAddressesViewModel(getAddressesAsCollectionUseCase: GetAddressesAsCollectionUseCase) :
    ViewModel() {

    private val _state = MutableLiveData<ViewState>(ViewState.LOADING)
    val state: LiveData<ViewState> get() = _state

    val addressesList: LiveData<List<DiffUtilItem>> get() = _addressesList
    private val _addressesList = MutableLiveData<List<DiffUtilItem>>()

    init {
        viewModelScope.launch {
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

    enum class ViewState {
        DEFAULT,
        LOADING,
        NO_ADDRESSES
    }
}