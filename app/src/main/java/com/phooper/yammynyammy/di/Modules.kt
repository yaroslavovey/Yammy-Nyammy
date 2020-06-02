package com.phooper.yammynyammy.di

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.data.repositories.UserRepository
import com.phooper.yammynyammy.viewmodels.LoginViewModel
import com.phooper.yammynyammy.viewmodels.MainContainerViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val firebaseModule = module {
    single { FirebaseAuth.getInstance() }
    //Google sign in options
    single {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(androidContext().resources.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }
    single {
        GoogleSignIn.getClient(androidContext(), get())
    }
    single { FirebaseDatabase.getInstance() }
}

val repositoryModule = module {
    single {
        UserRepository(get(), get())
    }
}

val viewModelModule = module {
    viewModel { MainContainerViewModel() }
    viewModel { LoginViewModel(get()) }
}


