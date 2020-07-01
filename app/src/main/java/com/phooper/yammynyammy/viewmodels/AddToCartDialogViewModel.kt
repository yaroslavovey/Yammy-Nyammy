package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phooper.yammynyammy.domain.usecases.AddProductsToCartUseCase
import kotlinx.coroutines.launch

open class AddToCartDialogViewModel(
    private val productId: Int,
    private val addProductsToCartUseCase: AddProductsToCartUseCase
) : ViewModel() {

    private val _itemCount = MutableLiveData(1)
    val itemCount: LiveData<Int> get() = _itemCount

    fun increaseItemCountByOne() {
        if (_itemCount.value in 1..98)
            _itemCount.postValue(_itemCount.value?.plus(1))
    }

    fun decreaseItemCountByOne() {
        if (_itemCount.value in 2..99)
            _itemCount.value = _itemCount.value?.minus(1)
    }

    fun addProductsToCart() {
        viewModelScope.launch {
            addProductsToCartUseCase.execute(productId, _itemCount.value!!)
        }
    }


}
