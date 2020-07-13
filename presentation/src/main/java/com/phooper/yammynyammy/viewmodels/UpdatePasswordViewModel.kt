package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ph00.domain.usecases.ReauthenticateUseCase
import com.ph00.domain.usecases.UpdateUserPasswordUseCase
import com.phooper.yammynyammy.utils.Event
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import timber.log.Timber

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

    fun updatePassword(currentPassword: String, newPassword: String) {
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
}