package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ph00.domain.models.UserModel
import com.ph00.domain.usecases.AddOrderUseCase
import com.ph00.domain.usecases.GetUserDataUseCase
import com.ph00.domain.usecases.SetUserDataUseCase
import com.phooper.yammynyammy.entities.User
import com.phooper.yammynyammy.utils.Event
import com.phooper.yammynyammy.utils.toPresentation
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.plus

@FlowPreview
@ExperimentalCoroutinesApi
class MakeOrderViewModel(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val setUserDataUseCase: SetUserDataUseCase,
    private val addOrderUseCase: AddOrderUseCase
) : ViewModel() {

    private val _state = MutableLiveData<ViewState>(ViewState.LOADING)
    val state: LiveData<ViewState> get() = _state

    private val _event = MutableLiveData<Event<ViewEvent>>()
    val event: LiveData<Event<ViewEvent>> get() = _event

    private val _userData = MutableLiveData<User>()
    val userData: LiveData<User> get() = _userData

    init {
        loadUser()
    }

    fun loadUser() {
        getUserDataUseCase
            .execute()
            .buffer()
            .onStart { _state.value = ViewState.LOADING }
            .onCompletion { _state.value = ViewState.DEFAULT }
            .onEach { userModel -> _userData.value = userModel.toPresentation() }
            .catch { _state.value = ViewState.NO_NETWORK }
            .launchIn(viewModelScope)
    }

    fun saveUserAndMakeOrder(name: String, phone: String, address: String) {
        setUserDataUseCase
            .execute(UserModel(name = name, phoneNum = phone))
            .buffer()
            .onStart { _state.value = ViewState.LOADING }
            .onEach {
                //Make order if user data updated
                addOrder(address)
            }
            .catch { _event.value = Event(ViewEvent.FAILURE) }
            .launchIn(viewModelScope)
    }

    private fun addOrder(address: String) {
        addOrderUseCase
            .execute(address)
            .buffer()
            .onEach { _event.postValue(Event(ViewEvent.SUCCESS)) }
            .catch { _event.postValue(Event(ViewEvent.FAILURE)) }
            .launchIn(viewModelScope + IO)
    }

    enum class ViewState {
        DEFAULT,
        LOADING,
        NO_NETWORK
    }

    enum class ViewEvent {
        FAILURE,
        SUCCESS
    }
}