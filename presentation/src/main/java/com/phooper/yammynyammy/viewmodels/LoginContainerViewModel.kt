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
class LoginContainerViewModel(isUserSignedInUseCase: GetIsUserSignedInUseCase) : ViewModel() {

    private val _event = MutableLiveData<Event<ViewEvent>>()
    val event: LiveData<Event<ViewEvent>> get() = _event

    init {
        isUserSignedInUseCase
            .execute()
            .onEach { if (it) _event.value = Event(ViewEvent.NAVIGATE_TO_MAIN_ACTIVITY) }
            .launchIn(viewModelScope)
    }

    enum class ViewEvent {
        NAVIGATE_TO_MAIN_ACTIVITY
    }
}

