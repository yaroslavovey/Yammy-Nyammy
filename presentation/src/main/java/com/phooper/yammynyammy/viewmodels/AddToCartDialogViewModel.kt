package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.*
import com.ph00.domain.usecases.AddProductsToCartUseCase
import com.phooper.yammynyammy.utils.Event
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.plus

@FlowPreview
@ExperimentalCoroutinesApi
open class AddToCartDialogViewModel(
    private val productId: Int,
    private val addProductsToCartUseCase: AddProductsToCartUseCase
) : ViewModel() {

    @Suppress("PropertyName")
    protected val _itemCount = MutableLiveData(1)
    val itemCount: LiveData<String> = Transformations.map(_itemCount) { it.toString() }

    private val _event = MutableLiveData<Event<ViewEvent>>()
    val event: LiveData<Event<ViewEvent>> get() = _event

    fun increaseItemCountByOne() {
        if (_itemCount.value in 1..98)
            _itemCount.postValue(_itemCount.value?.plus(1))
    }

    fun decreaseItemCountByOne() {
        if (_itemCount.value in 2..99)
            _itemCount.value = _itemCount.value?.minus(1)
    }

    fun addProductsToCart() {
        _itemCount.value?.let { itemCount ->
            addProductsToCartUseCase
                .execute(productId, itemCount)
                .onEach { _event.postValue(Event(ViewEvent.SUCCESS)) }
                .catch { _event.postValue(Event(ViewEvent.FAILURE)) }
                .launchIn(viewModelScope + IO)
        }
    }

    enum class ViewEvent {
        SUCCESS,
        FAILURE
    }

}
