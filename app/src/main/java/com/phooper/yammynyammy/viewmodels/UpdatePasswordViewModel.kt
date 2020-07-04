package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phooper.yammynyammy.domain.usecases.ReauthenticateUseCase
import com.phooper.yammynyammy.domain.usecases.UpdateUserPasswordUseCase
import com.phooper.yammynyammy.utils.Event
import kotlinx.coroutines.launch
import timber.log.Timber

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
        _state.value = ViewState.LOADING
        viewModelScope.launch {
            reauthenticateUseCase.execute(currentPassword)?.let {
                updateUserPasswordUseCase.execute(newPassword)?.let {
                    _state.postValue(ViewState.DEFAULT)
                    _event.postValue(Event(ViewEvent.SUCCESS))
                    return@launch
                }
            }
            _state.postValue(ViewState.DEFAULT)
            _event.postValue(Event(ViewEvent.FAILURE))
        }
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