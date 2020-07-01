package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.*
import com.livermor.delegateadapter.delegate.diff.DiffUtilItem
import com.phooper.yammynyammy.data.models.AddAddressButton
import com.phooper.yammynyammy.domain.usecases.GetAddressesLiveData
import kotlinx.coroutines.Dispatchers.IO

class MyAddressesViewModel(getAddressesLiveData: GetAddressesLiveData) : ViewModel() {

    private val _state = MutableLiveData<ViewState>()
    val state: LiveData<ViewState> get() = _state

    val addressesList: LiveData<List<DiffUtilItem>> =
        getAddressesLiveData.execute().switchMap { listAddresses ->
            liveData(context = viewModelScope.coroutineContext + IO) {
                _state.postValue(ViewState.LOADING)
                if (listAddresses.isNullOrEmpty()) {
                    _state.postValue(ViewState.NO_ADDRESSES)
                } else {
                    emit(listAddresses.plus(AddAddressButton()))
                    _state.postValue(ViewState.DEFAULT)
                }
            }
        }

    enum class ViewState {
        DEFAULT,
        LOADING,
        NO_ADDRESSES
    }
}