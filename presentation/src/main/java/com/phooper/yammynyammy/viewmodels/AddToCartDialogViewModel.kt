package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.*
import com.ph00.domain.usecases.AddProductsToCartUseCase
import kotlinx.coroutines.launch

open class AddToCartDialogViewModel(
    private val productId: Int,
    private val addProductsToCartUseCase: AddProductsToCartUseCase
) : ViewModel() {

    @Suppress("PropertyName")
    protected val _itemCount = MutableLiveData(1)
    val itemCount: LiveData<String> = Transformations.map(_itemCount) { it.toString() }

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
            _itemCount.value?.let { itemCount ->
                addProductsToCartUseCase.execute(productId, itemCount)
            }
        }
    }

}
