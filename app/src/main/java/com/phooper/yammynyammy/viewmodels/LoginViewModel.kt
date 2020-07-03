package com.phooper.yammynyammy.viewmodels

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.phooper.yammynyammy.domain.models.User
import com.phooper.yammynyammy.domain.usecases.*
import com.phooper.yammynyammy.utils.Constants
import com.phooper.yammynyammy.utils.Event
import kotlinx.coroutines.launch

class LoginViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val setUserDataUseCase: SetUserDataUseCase,
    private val signInViaEmailAndPasswordUseCase: SignInViaEmailAndPasswordUseCase,
    private val signInViaGoogleUseCase: SignInViaGoogleUseCase
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
        getCurrentUserUseCase.execute()?.let {
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


    fun handleSignInViaEmail(email: String, password: String) {
        _state.value = ViewState.LOADING
        viewModelScope.launch {
            signInViaEmailAndPasswordUseCase.execute(email, password)?.let {
                checkCurrentUser()
                return@launch
            }
            _event.postValue(Event(ViewEvent.AUTH_ERROR))
            _state.postValue(ViewState.DEFAULT)
        }
    }

    fun handleOnActivityResult(requestCode: Int, data: Intent?) {
        if (requestCode == Constants.G_AUTH_REQUEST_CODE) {
            Auth.GoogleSignInApi.getSignInResultFromIntent(data)?.let { result ->
                if (result.isSuccess) {
                    result.signInAccount?.let {
                        handleSignInViaGoogle(it)
                    }
                } else {
                    _event.postValue(Event(ViewEvent.AUTH_ERROR))
                }
            }
        }
    }

    private fun handleSignInViaGoogle(signInAccount: GoogleSignInAccount) {
        _state.value = ViewState.LOADING
        viewModelScope.launch {
            _username.postValue(signInAccount.displayName)
            signInViaGoogleUseCase.execute(signInAccount)?.let {
                checkCurrentUser()
                return@launch
            }
            _event.postValue(Event(ViewEvent.AUTH_ERROR))
            _state.postValue(ViewState.DEFAULT)
        }
    }

    fun handleSignUpViaEmail(
        email: String,
        password: String
    ) {
        _state.value = ViewState.LOADING
        viewModelScope.launch {
            signInViaEmailAndPasswordUseCase.execute(email, password)?.let {
                _event.postValue(Event(ViewEvent.NAVIGATE_TO_PHONE_NAME_FRAGMENT_FROM_REGISTER))
                _state.postValue(ViewState.DEFAULT)
                return@launch
            }
            _event.postValue(Event(ViewEvent.AUTH_ERROR))
            _state.postValue(ViewState.DEFAULT)
        }
    }

    fun handleAddUserData(name: String, phoneNum: String) {
        _state.value = ViewState.LOADING
        viewModelScope.launch {
            setUserDataUseCase.execute(User(name = name, phoneNum = phoneNum))
                ?.let {
                    _event.postValue(Event(ViewEvent.NAVIGATE_TO_MAIN_ACTIVITY))
                    return@launch
                }
            _event.postValue(Event(ViewEvent.AUTH_ERROR))
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
        AUTH_ERROR
    }
}

