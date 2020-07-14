package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ph00.domain.usecases.ReauthenticateUseCase
import com.ph00.domain.usecases.UpdateUserPasswordUseCase
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.utils.Event
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
class UpdatePasswordViewModel(
    private val updateUserPasswordUseCase: UpdateUserPasswordUseCase,
    private val reauthenticateUseCase: ReauthenticateUseCase
) :
    ViewModel() {

    private val _state = MutableLiveData<ViewState>(ViewState.DEFAULT)
    val state: LiveData<ViewState> get() = _state

    private val _event = MutableLiveData<Event<ViewEvent>>()
    val event: LiveData<Event<ViewEvent>> get() = _event

    private val _inputValidator = MutableLiveData<InputValidator>()
    val inputValidator: LiveData<InputValidator> get() = _inputValidator

    fun updatePassword(
        currentPassword: String,
        newPassword: String,
        newPasswordRepeat: String
    ) {
        if (currentPassword.isEmpty() || newPassword.isEmpty() || newPasswordRepeat.isEmpty()) {
            _inputValidator.value = InputValidator(
                currentPasswordInputErrorResId = if (currentPassword.isEmpty()) R.string.fill_password else null,
                newPasswordInputErrorResId = if (newPassword.isEmpty()) R.string.fill_password else null,
                newPasswordRepeatInputErrorResId = if (newPasswordRepeat.isEmpty()) R.string.fill_password else null
            )
            return
        }

        if (newPassword != newPasswordRepeat) {
            _inputValidator.value =
                InputValidator(
                    newPasswordInputErrorResId = R.string.passwords_does_not_match,
                    newPasswordRepeatInputErrorResId = R.string.passwords_does_not_match
                )
            return
        }

        if (newPassword.length < 6) {
            _inputValidator.value =
                InputValidator(
                    newPasswordInputErrorResId = R.string.passwords_are_too_short,
                    newPasswordRepeatInputErrorResId = R.string.passwords_are_too_short
                )
            return
        }

        reauthenticateUseCase
            .execute(currentPassword)
            .flatMapConcat { updateUserPasswordUseCase.execute(newPassword) }
            .onStart { _state.value = ViewState.LOADING }
            .onCompletion { _state.value = ViewState.DEFAULT }
            .onEach { _event.value = Event(ViewEvent.SUCCESS) }
            .catch { _event.value = Event(ViewEvent.FAILURE) }
            .launchIn(viewModelScope)

    }

    enum class ViewState {
        LOADING,
        DEFAULT
    }

    enum class ViewEvent {
        SUCCESS,
        FAILURE
    }

    data class InputValidator(
        val currentPasswordInputErrorResId: Int? = null,
        val newPasswordInputErrorResId: Int? = null,
        val newPasswordRepeatInputErrorResId: Int? = null
    )

}