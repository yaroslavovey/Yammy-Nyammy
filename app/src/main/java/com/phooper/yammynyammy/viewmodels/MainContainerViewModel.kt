package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phooper.yammynyammy.domain.usecases.GetAllCartProductsCountLiveDataUseCase

class MainContainerViewModel(
    private val getAllCartProductsCountLiveDataUseCase: GetAllCartProductsCountLiveDataUseCase
) : ViewModel() {

    private val _selectedAddress = MutableLiveData<String>()
    val selectedAddress: LiveData<String> get() = _selectedAddress

    val cartItemCount: LiveData<Int> =
        getAllCartProductsCountLiveDataUseCase.execute(viewModelScope)

    fun selectAddress(str: String) {
        _selectedAddress.value = str
    }

    fun resetAddress() {
        _selectedAddress.value = ""
    }


}