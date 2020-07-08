package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ph00.domain.models.OrderAddressAndStatusModel
import com.ph00.domain.models.OrderModel
import com.ph00.domain.models.UserModel
import com.ph00.domain.usecases.*
import com.phooper.yammynyammy.entities.User
import com.phooper.yammynyammy.utils.Event
import com.phooper.yammynyammy.utils.toPresentation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MakeOrderViewModel(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val setUserDataUseCase: SetUserDataUseCase,
    private val getAllProductInCartUseCase: GetAllProductInCartUseCase,
    private val dropCartUseCase: DropCartUseCase,
    private val addOrderUseCase: AddOrderUseCase,
    private val getDeliveryPriceUseCase: GetDeliveryPriceUseCase
) : ViewModel() {

    private val _state = MutableLiveData<ViewState>(ViewState.LOADING)
    val state: LiveData<ViewState> get() = _state

    private val _event = MutableLiveData<Event<ViewEvent>>()
    val event: LiveData<Event<ViewEvent>> get() = _event

    private val _userData = MutableLiveData<User>()
    val userData: LiveData<User> get() = _userData

    init {
        viewModelScope.launch {
            loadUser()
        }
    }

    private suspend fun loadUser() {
        getUserDataUseCase.execute()?.let {
            _userData.postValue(it.toPresentation())
            _state.postValue(ViewState.DEFAULT)
            return
        }
        _event.postValue(Event(ViewEvent.FAILURE))
        delay(5000)
        loadUser()
    }

    fun makeOrder(name: String, phone: String, address: String) {
        _state.value = ViewState.LOADING
        viewModelScope.launch {
            //Update current user data
            setUserDataUseCase.execute(
                UserModel(
                    name = name,
                    phoneNum = phone
                )
            )?.let {
                //Make order
                getAllProductInCartUseCase.execute()?.let { productInCartList ->
                    addOrderUseCase.execute(
                        OrderModel(
                            addressAndStatus = OrderAddressAndStatusModel(address = address),
                            products = productInCartList,
                            deliveryPrice = getDeliveryPriceUseCase.execute()
                        )
                    )?.let {
                        dropCartUseCase.execute()
                        _event.postValue(Event(ViewEvent.SUCCESS))
                        return@launch
                    }
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