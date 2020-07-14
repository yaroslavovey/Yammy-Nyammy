package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ph00.domain.models.UserModel
import com.ph00.domain.usecases.AddOrderUseCase
import com.ph00.domain.usecases.GetUserDataUseCase
import com.ph00.domain.usecases.SetUserDataUseCase
import com.phooper.yammynyammy.R
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

    private val _inputValidator = MutableLiveData<InputValidator>()
    val inputValidator: LiveData<InputValidator> get() = _inputValidator

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

        if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            _inputValidator.value = InputValidator(
                nameInputErrorResId = if (name.isEmpty()) R.string.fill_name else null,
                phoneNumInputErrorResId = if (phone.isEmpty()) R.string.fill_phone else null,
                addressInputErrorResId = if (address.isEmpty()) R.string.fill_address else null
            )
            return
        }


        setUserDataUseCase
            .execute(UserModel(name = name, phoneNum = phone))
            .flatMapConcat { addOrderUseCase.execute(address) }
            .onStart { _state.postValue(ViewState.LOADING) }
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

    data class InputValidator(
        val nameInputErrorResId: Int? = null,
        val phoneNumInputErrorResId: Int? = null,
        val addressInputErrorResId: Int? = null
    )

}