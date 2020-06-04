package com.phooper.yammynyammy.viewmodels

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.firestore.ktx.toObject
import com.phooper.yammynyammy.data.models.User
import com.phooper.yammynyammy.data.repositories.UserRepository
import com.phooper.yammynyammy.utils.Constants
import com.phooper.yammynyammy.utils.Event
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _state = MutableLiveData<LoginState>()
    val state: LiveData<LoginState> get() = _state

    private val _event = MutableLiveData<Event<LoginEvent>>()
    val event: LiveData<Event<LoginEvent>> get() = _event

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username

    init {
        checkCurrentUser()
    }

    private fun checkCurrentUser() {
        viewModelScope.launch(IO) {
            //Check if user signed in
            userRepository.getCurrentUser()?.let { currentUser ->
                //If signed in and has no phone & name
                if (userRepository.getUserData(currentUser.uid)?.toObject<User>() == null) {
                    _event.postValue(Event(LoginEvent.NAVIGATE_TO_PHONE_NAME_FRAGMENT_FROM_LOGIN))
                    _state.postValue(LoginState.DEFAULT)
                    return@launch
                }
                _event.postValue(Event(LoginEvent.NAVIGATE_TO_MAIN_ACTIVITY))
                return@launch
            }
            _state.postValue(LoginState.DEFAULT)
        }
    }

    fun handleSignInViaEmail(email: String, password: String) {
        _state.value = LoginState.LOADING
        viewModelScope.launch(IO) {
            userRepository.signInViaEmailAndPassword(email, password)?.let {
                checkCurrentUser()
                return@launch
            }
            _event.postValue(Event(LoginEvent.AUTH_ERROR))
            _state.postValue(LoginState.DEFAULT)
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
                    _event.postValue(Event(LoginEvent.AUTH_ERROR))
                }
            }
        }
    }

    private fun handleSignInViaGoogle(signInAccount: GoogleSignInAccount) {
        _state.value = LoginState.LOADING
        viewModelScope.launch(IO) {
            _username.postValue(signInAccount.displayName)
            userRepository.signInViaGoogle(signInAccount)?.let {
                checkCurrentUser()
                return@launch
            }
            _event.postValue(Event(LoginEvent.AUTH_ERROR))
            _state.postValue(LoginState.DEFAULT)
        }
    }

    fun handleSignUpViaEmail(
        email: String,
        password: String
    ) {
        _state.value = LoginState.LOADING
        viewModelScope.launch(IO) {
            userRepository.signUpViaEmailAndPassword(email, password)?.let {
                _event.postValue(Event(LoginEvent.NAVIGATE_TO_PHONE_NAME_FRAGMENT_FROM_REGISTER))
                _state.postValue(LoginState.DEFAULT)
                return@launch
            }
            _event.postValue(Event(LoginEvent.AUTH_ERROR))
            _state.postValue(LoginState.DEFAULT)
        }
    }

    fun handleAddUserData(name: String, phoneNum: String) {
        _state.value = LoginState.LOADING
        viewModelScope.launch(IO) {
            userRepository.getCurrentUser()?.let { currentUser ->
                userRepository.addUserData(
                    currentUser.uid,
                    User(email = currentUser.email!!, name = name, phoneNum = phoneNum.toInt())
                )?.let {
                    _event.postValue(Event(LoginEvent.NAVIGATE_TO_MAIN_ACTIVITY))
                    return@launch
                }
                _event.postValue(Event(LoginEvent.AUTH_ERROR))
                _state.postValue(LoginState.DEFAULT)
            }
        }
    }

    enum class LoginState {
        DEFAULT,
        LOADING
    }

    enum class LoginEvent {
        NAVIGATE_TO_MAIN_ACTIVITY,
        NAVIGATE_TO_PHONE_NAME_FRAGMENT_FROM_LOGIN,
        NAVIGATE_TO_PHONE_NAME_FRAGMENT_FROM_REGISTER,
        AUTH_ERROR
    }
}

