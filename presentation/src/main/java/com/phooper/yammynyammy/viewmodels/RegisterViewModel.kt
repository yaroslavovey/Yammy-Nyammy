package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ph00.domain.usecases.SignUpViaEmailAndPasswordUseCase
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.utils.Event
import com.phooper.yammynyammy.utils.isEmailValid
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

@ExperimentalCoroutinesApi
class RegisterViewModel(
    private val signUpViaEmailAndPasswordUseCase: SignUpViaEmailAndPasswordUseCase
) : ViewModel() {

    private val _state = MutableLiveData<ViewState>()
    val state: LiveData<ViewState> get() = _state

    private val _event = MutableLiveData<Event<ViewEvent>>()
    val event: LiveData<Event<ViewEvent>> get() = _event

    private val _inputValidator = MutableLiveData<InputValidator>()
    val inputValidator: LiveData<InputValidator> get() = _inputValidator

    fun signUpViaEmail(email: String, password: String, passwordRepeat: String) {
        if (email.isEmpty() || password.isEmpty() || password.isEmpty()) {
            _inputValidator.value = InputValidator(
                emailInputErrorResId = if (email.isEmpty()) R.string.fill_email else null,
                passwordInputErrorResId = if (password.isEmpty()) R.string.fill_password else null,
                passwordRepeatInputErrorResId = if (passwordRepeat.isEmpty()) R.string.fill_password else null
            )
            return
        }

        if (!email.isEmailValid()) {
            _inputValidator.value = InputValidator(emailInputErrorResId = R.string.invalid_email)
            return
        }

        if (password != passwordRepeat) {
            _inputValidator.value = InputValidator(
                passwordInputErrorResId = R.string.passwords_does_not_match,
                passwordRepeatInputErrorResId = R.string.passwords_does_not_match
            )
            return
        }

        if (password.length < 6) {
            _inputValidator.value = InputValidator(
                passwordInputErrorResId = R.string.passwords_are_too_short,
                passwordRepeatInputErrorResId = R.string.passwords_are_too_short
            )
            return
        }

        signUpViaEmailAndPasswordUseCase
            .execute(email, password)
            .onStart { _state.value = ViewState.LOADING }
            .onCompletion { _state.value = ViewState.DEFAULT }
            .catch { _event.value = Event(ViewEvent.ERROR) }
            .launchIn(viewModelScope)
    }

    enum class ViewEvent {
        ERROR
    }

    enum class ViewState {
        DEFAULT,
        LOADING
    }

    data class InputValidator(
        val emailInputErrorResId: Int? = null,
        val passwordInputErrorResId: Int? = null,
        val passwordRepeatInputErrorResId: Int? = null
    )

}