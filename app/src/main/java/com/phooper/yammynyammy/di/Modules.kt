package com.phooper.yammynyammy.di

import com.google.firebase.auth.FirebaseAuth
import com.phooper.yammynyammy.viewmodels.MainContainerViewModel
import com.phooper.yammynyammy.viewmodels.SplashScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainContainerViewModel() }
    viewModel { SplashScreenViewModel() }
}

val firebaseModule = module {
    single { FirebaseAuth.getInstance() }
}
