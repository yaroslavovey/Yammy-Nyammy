package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import com.livermor.delegateadapter.delegate.diff.DiffUtilItem
import com.phooper.yammynyammy.data.models.AddAddressButton
import com.phooper.yammynyammy.data.models.Address
import com.phooper.yammynyammy.data.repositories.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyAddressesViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _state = MutableLiveData<ViewState>()
    val state: LiveData<ViewState> get() = _state

    private val _addressesList = MutableLiveData<List<DiffUtilItem>>()
    val addressesList: LiveData<List<DiffUtilItem>> get() = _addressesList

    init {
        viewModelScope.launch {
            startListeningForAddresses()
        }
    }

    private suspend fun startListeningForAddresses() {
        withContext(IO) {
            _state.postValue(ViewState.LOADING)
            userRepository.getUserAddressesCollection()
                ?.addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
                    if (e != null) {
                        return@EventListener
                    }
                    value?.documents?.mapNotNull { it.toObject<Address>() }
                        .let { listAddresses ->
                            if (listAddresses.isNullOrEmpty()) {
                                _state.postValue(ViewState.NO_ADDRESSES)
                            } else {
                                _addressesList.postValue(listAddresses.plus(AddAddressButton()))
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