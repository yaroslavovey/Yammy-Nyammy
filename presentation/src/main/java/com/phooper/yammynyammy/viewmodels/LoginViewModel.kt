package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ph00.domain.models.UserModel
import com.ph00.domain.usecases.*
import com.phooper.yammynyammy.utils.Event
import kotlinx.coroutines.launch

class LoginViewModel(
    private val getCurrentUserUidUseCase: GetCurrentUserUidUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val setUserDataUseCase: SetUserDataUseCase,
    private val signInViaEmailAndPasswordUseCase: SignInViaEmailAndPasswordUseCase,
    private val signUpViaEmailAndPasswordUseCase: SignUpViaEmailAndPasswordUseCase
) : ViewModel() {

    private val _state = MutableLiveData<ViewState>()
    val state: LiveData<ViewState> get() = _state

    private val _event = MutableLiveData<Event<ViewEvent>>()
    val event: LiveData<Event<ViewEvent>> get() = _event

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username

    init {
        viewModelScope.launch {
            checkCurrentUser()
        }
    }

    private suspend fun checkCurrentUser() {
        //Check if user signed in
        getCurrentUserUidUseCase.execute()?.let {
            //If signed in and has no phone & name
            if (getUserDataUseCase.execute() == null) {
                _event.postValue(Event(ViewEvent.NAVIGATE_TO_PHONE_NAME_FRAGMENT_FROM_LOGIN))
                _state.postValue(ViewState.DEFAULT)
                return
            }
            //If passed all checks -> User is signed in -> Nav To Main Activity
            _event.postValue(Event(ViewEvent.NAVIGATE_TO_MAIN_ACTIVITY))
            return
        }
        _state.postValue(ViewState.DEFAULT)
    }


    fun signInViaEmail(email: String, password: String) {
        _state.value = ViewState.LOADING
        viewModelScope.launch {
            signInViaEmailAndPasswordUseCase.execute(email, password)?.let {
                checkCurrentUser()
                return@launch
            }
            _event.postValue(Event(ViewEvent.ERROR))
            _state.postValue(ViewState.DEFAULT)
        }
    }

    fun signUpViaEmail(
        email: String,
        password: String
    ) {
        _state.value = ViewState.LOADING
        viewModelScope.launch {
            signUpViaEmailAndPasswordUseCase.execute(email, password)?.let {
                _event.postValue(Event(ViewEvent.NAVIGATE_TO_PHONE_NAME_FRAGMENT_FROM_REGISTER))
                _state.postValue(ViewState.DEFAULT)
                return@launch
            }
            _event.postValue(Event(ViewEvent.ERROR))
            _state.postValue(ViewState.DEFAULT)
        }
    }

    fun addUserData(name: String, phoneNum: String) {
        _state.value = ViewState.LOADING
        viewModelScope.launch {
            setUserDataUseCase.execute(
                UserModel(
                    name = name,
                    phoneNum = phoneNum
                )
            )
                ?.let {
                    _event.postValue(Event(ViewEvent.NAVIGATE_TO_MAIN_ACTIVITY))
                    return@launch
                }
            _event.postValue(Event(ViewEvent.ERROR))
            _state.postValue(ViewState.DEFAULT)
        }
    }

    enum class ViewState {
        DEFAULT,
        LOADING
    }

    enum class ViewEvent {
        NAVIGATE_TO_MAIN_ACTIVITY,
        NAVIGATE_TO_PHONE_NAME_FRAGMENT_FROM_LOGIN,
        NAVIGATE_TO_PHONE_NAME_FRAGMENT_FROM_REGISTER,
        ERROR
    }
}

