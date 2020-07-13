package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ph00.domain.usecases.GetIsUserSignedInUseCase
import com.ph00.domain.usecases.SignInViaEmailAndPasswordUseCase
import com.ph00.domain.usecases.SignUpViaEmailAndPasswordUseCase
import com.phooper.yammynyammy.utils.Event
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
class LoginViewModel(
    isUserSignedInUseCase: GetIsUserSignedInUseCase,
    private val signInViaEmailAndPasswordUseCase: SignInViaEmailAndPasswordUseCase,
    private val signUpViaEmailAndPasswordUseCase: SignUpViaEmailAndPasswordUseCase
) : ViewModel() {

    private val _state = MutableLiveData<ViewState>()
    val state: LiveData<ViewState> get() = _state

    private val _event = MutableLiveData<Event<ViewEvent>>()
    val event: LiveData<Event<ViewEvent>> get() = _event

    init {
        isUserSignedInUseCase
            .execute()
            .buffer()
            .onEach { if (it) _event.value = Event(ViewEvent.NAVIGATE_TO_MAIN_ACTIVITY) }
            .launchIn(viewModelScope)
    }

    fun signInViaEmail(email: String, password: String) {
        signInViaEmailAndPasswordUseCase
            .execute(email, password)
            .onStart { _state.value = ViewState.LOADING }
            .onCompletion { _state.value = ViewState.DEFAULT }
            .catch { _event.value = Event(ViewEvent.ERROR) }
            .launchIn(viewModelScope)
    }

    fun signUpViaEmail(email: String, password: String) {
        signUpViaEmailAndPasswordUseCase
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
        NAVIGATE_TO_MAIN_ACTIVITY,
        ERROR
    }
}

