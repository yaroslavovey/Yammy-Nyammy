package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ph00.domain.usecases.SignInViaEmailAndPasswordUseCase
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.utils.Event
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

@ExperimentalCoroutinesApi
class LoginViewModel(
    private val signInViaEmailAndPasswordUseCase: SignInViaEmailAndPasswordUseCase
) : ViewModel() {

    private val _state = MutableLiveData<ViewState>()
    val state: LiveData<ViewState> get() = _state

    private val _event = MutableLiveData<Event<ViewEvent>>()
    val event: LiveData<Event<ViewEvent>> get() = _event

    private val _inputValidator = MutableLiveData<InputValidator>()
    val inputValidator: LiveData<InputValidator> get() = _inputValidator

    fun signInViaEmail(email: String, password: String) {

        if (email.isEmpty() || password.isEmpty()) {
            _inputValidator.value = InputValidator(
                emailInputErrorResId = if (email.isEmpty()) R.string.fill_email else null,
                passwordInputErrorResId = if (password.isEmpty()) R.string.fill_password else null
            )
            return
        }

        signInViaEmailAndPasswordUseCase
            .execute(email, password)
            .onStart { _state.value = ViewState.LOADING }
            .onCompletion { _state.value = ViewState.DEFAULT }
            .catch { _event.value = Event(ViewEvent.ERROR) }
            .launchIn(viewModelScope)

    }

    enum class ViewState {
        DEFAULT,
        LOADING
    }

    enum class ViewEvent {
        ERROR
    }

    data class InputValidator(
        val emailInputErrorResId: Int? = null,
        val passwordInputErrorResId: Int? = null
    )
}