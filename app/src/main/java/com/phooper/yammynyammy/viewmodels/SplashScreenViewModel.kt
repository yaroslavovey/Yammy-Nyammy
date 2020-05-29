package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phooper.yammynyammy.data.repositories.UserRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class SplashScreenViewModel(private val userRepository: UserRepository) : ViewModel() {

    val isSignedIn = MutableLiveData<Boolean>()

    init {
        checkUser()
    }

    private fun checkUser() {
        viewModelScope.launch {
            isSignedIn.value = userRepository
                .getCurrentUser() != null
        }

    }

}

