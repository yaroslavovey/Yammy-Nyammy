package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phooper.yammynyammy.domain.models.Order
import com.phooper.yammynyammy.domain.models.User
import com.phooper.yammynyammy.domain.usecases.*
import com.phooper.yammynyammy.utils.Event
import kotlinx.coroutines.launch

class MakeOrderViewModel(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val setUserDataUseCase: SetUserDataUseCase,
    private val getAllProductInCartUseCase: GetAllProductInCartUseCase,
    private val dropCartUseCase: DropCartUseCase,
    private val addOrderUseCase: AddOrderUseCase
) : ViewModel() {

    private val _state = MutableLiveData<ViewState>()
    val state: LiveData<ViewState> get() = _state

    private val _event = MutableLiveData<Event<ViewEvent>>()
    val event: LiveData<Event<ViewEvent>> get() = _event


    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username

    private val _phoneNum = MutableLiveData<String>()
    val phoneNum: LiveData<String> get() = _phoneNum

    init {
        viewModelScope.launch {
            loadUserData()
        }
    }

    private suspend fun loadUserData() {
        _state.postValue(ViewState.LOADING)
        getUserDataUseCase.execute()?.let {
            _phoneNum.postValue(it.phoneNum)
            _username.postValue(it.name)
        }
        _state.postValue(ViewState.DEFAULT)
    }

    fun makeOrder(name: String, phone: String, address: String) {
        _state.value = ViewState.LOADING
        viewModelScope.launch {
            //Update current user data
            setUserDataUseCase.execute(User(name = name, phoneNum = phone))
            //Make order
            getAllProductInCartUseCase.execute()?.let { productInCartList ->
                addOrderUseCase.execute(
                    Order(
                        address = address,
                        products = productInCartList
                    )
                )?.let {
                    dropCartUseCase.execute()
                    _event.postValue(Event(ViewEvent.SUCCESS))
                    return@launch
                }
            }
            _state.postValue(ViewState.DEFAULT)
            _event.postValue(Event(ViewEvent.FAILURE))
        }
    }


    enum class ViewState {
        DEFAULT,
        LOADING,
    }

    enum class ViewEvent {
        FAILURE,
        SUCCESS
    }
}