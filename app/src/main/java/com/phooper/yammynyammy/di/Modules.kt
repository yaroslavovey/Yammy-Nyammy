package com.phooper.yammynyammy.di

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.viewmodels.MainContainerViewModel
import com.phooper.yammynyammy.viewmodels.SplashScreenViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainContainerViewModel() }
    viewModel { SplashScreenViewModel() }
}

val firebaseModule = module {
    single { FirebaseAuth.getInstance() }
    single {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(androidContext().resources.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }
    single {
        GoogleSignIn.getClient(androidContext(), get())
    }
}

