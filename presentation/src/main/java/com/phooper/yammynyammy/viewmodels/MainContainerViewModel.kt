package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.*
import com.ph00.domain.usecases.GetAddressByUidUseCase
import com.ph00.domain.usecases.GetAllCartProductIdAndCountAsFLowUseCase
import com.ph00.domain.usecases.GetIsUserSignedInFlow
import com.phooper.yammynyammy.utils.Event
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainContainerViewModel(
    getAllCartProductIdAndCountAsFLowUseCase: GetAllCartProductIdAndCountAsFLowUseCase,
    private val getAddressByUidUseCase: GetAddressByUidUseCase,
    private val getIsUserSignedInFlow: GetIsUserSignedInFlow
) : ViewModel() {

    private val _selectedAddress = MutableLiveData<String>()
    val selectedAddress: LiveData<String> get() = _selectedAddress

    val cartItemCount: LiveData<Int> =
        getAllCartProductIdAndCountAsFLowUseCase.execute().asLiveData(IO)
            .map { list -> if (list.isNullOrEmpty()) 0 else list.sumBy { it.count } }

    val userIsSignedIn : LiveData<Boolean> = getIsUserSignedInFlow.execute().asLiveData(IO)

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

}