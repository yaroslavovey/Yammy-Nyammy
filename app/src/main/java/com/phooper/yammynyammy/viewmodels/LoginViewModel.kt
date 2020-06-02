package com.phooper.yammynyammy.viewmodels

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.phooper.yammynyammy.data.repositories.UserRepository
import com.phooper.yammynyammy.utils.Constants
import com.phooper.yammynyammy.utils.Event
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import timber.log.Timber

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
            if (userRepository.getCurrentUser() != null)
                _event.postValue(Event(LoginEvent.NAVIGATE_TO_MAIN_ACTIVITY))
            else
                _state.postValue(LoginState.DEFAULT)
        }
    }

    fun handleSignInViaEmail(email: String, password: String) {
        _state.value = LoginState.LOADING
        viewModelScope.launch(IO) {
            userRepository.signInViaEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {

                } else {
                    _event.postValue(Event(LoginEvent.AUTH_ERROR))
                }
                _state.postValue(LoginState.DEFAULT)
            }
        }
    }

    fun handleOnActivityResult(requestCode: Int, data: Intent?) {
        if (requestCode == Constants.G_AUTH_REQUEST_CODE) {
            Auth.GoogleSignInApi.getSignInResultFromIntent(data)?.let { result ->
                if (result.isSuccess) {
                    result.signInAccount?.let {
                        Timber.d(it.displayName)
                        _username.postValue(it.displayName)
                        _event.postValue(Event(LoginEvent.NAVIGATE_TO_PHONE_FRAGMENT))
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
            userRepository.signInViaGoogle(signInAccount).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _event.postValue(Event(LoginEvent.NAVIGATE_TO_MAIN_ACTIVITY))
                } else {
                    _event.postValue(Event(LoginEvent.AUTH_ERROR))
                }
            }
        }
    }

    enum class LoginState {
        DEFAULT,
        LOADING
    }

    enum class LoginEvent {
        NAVIGATE_TO_MAIN_ACTIVITY,
        NAVIGATE_TO_PHONE_FRAGMENT,
        AUTH_ERROR
    }
}

