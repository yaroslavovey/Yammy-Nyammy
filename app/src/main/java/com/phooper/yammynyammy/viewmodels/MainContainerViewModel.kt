package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainContainerViewModel : ViewModel() {

    private val _selectedAddress = MutableLiveData<String>()
    val selectedAddress: LiveData<String> get() = _selectedAddress

    fun selectAddress(str: String) {
        _selectedAddress.value = str
    }

    fun resetAddress(){
        _selectedAddress.value = ""
    }
}