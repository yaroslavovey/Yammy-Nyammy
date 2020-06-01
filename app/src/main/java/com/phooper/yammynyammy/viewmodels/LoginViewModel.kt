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
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _state = MutableLiveData<LoginState>()
    val state: LiveData<LoginState> get() = _state

    init {
        checkCurrentUser()
    }

    private fun checkCurrentUser() {
        viewModelScope.launch(IO) {
            _state.postValue(
                if (userRepository
                        .getCurrentUser() != null
                ) LoginState.AUTHENTICATED else LoginState.DEFAULT
            )
        }
    }

    fun handleSignInViaEmail(email: String, password: String) {
        _state.value = LoginState.LOADING
        viewModelScope.launch(IO) {
            userRepository.signInViaEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    _state.postValue(LoginState.DEFAULT)
                } else {
                    _state.postValue(LoginState.AUTH_ERROR)
                }
            }
        }
    }

    fun handleOnActivityResult(requestCode: Int, data: Intent?) {
        if (requestCode == Constants.G_AUTH_REQUEST_CODE) {
            Auth.GoogleSignInApi.getSignInResultFromIntent(data)?.let { result ->
                if (result.isSuccess) {
                    Timber.d("Logging in")
                    result.signInAccount?.let {
                        handleSignInViaGoogle(it)
                    }
                } else {
                    _state.postValue(LoginState.AUTH_ERROR)
                }
            }
        }
    }

    private fun handleSignInViaGoogle(signInAccount: GoogleSignInAccount) {
        _state.value = LoginState.LOADING
        viewModelScope.launch(IO) {
            userRepository.signInViaGoogle(signInAccount).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _state.postValue(LoginState.AUTHENTICATED)
                } else {
                    _state.postValue(LoginState.AUTH_ERROR)
                }
            }
        }
    }

    enum class LoginState {
        DEFAULT,
        AUTHENTICATED,
        AUTH_ERROR,
        LOADING
    }
}

