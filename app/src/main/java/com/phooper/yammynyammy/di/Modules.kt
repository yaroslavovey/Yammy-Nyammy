package com.phooper.yammynyammy.di

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.data.api.ShopApi
import com.phooper.yammynyammy.data.repositories.ProductsRepository
import com.phooper.yammynyammy.data.repositories.UserRepository
import com.phooper.yammynyammy.ui.adapters.ProductListAdapter
import com.phooper.yammynyammy.utils.Constants.Companion.BASE_URL
import com.phooper.yammynyammy.viewmodels.LoginViewModel
import com.phooper.yammynyammy.viewmodels.MainContainerViewModel
import com.phooper.yammynyammy.viewmodels.ProductListViewModel
import com.squareup.picasso.Picasso
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

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
    single { Firebase.firestore }
}

val netModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
    single { Picasso.Builder(androidContext()).build() }
}

val apiModule = module {
    single { get<Retrofit>().create(ShopApi::class.java) }
}

val repositoryModule = module {
    single {
        UserRepository(firebaseAuth = get(), firebaseFirestone = get())
    }
    single {
        ProductsRepository(shopApi = get())
    }
}

val viewModelModule = module {
    viewModel { MainContainerViewModel() }
    viewModel { LoginViewModel(userRepository = get()) }
    viewModel { ProductListViewModel(productsRepository = get()) }
}

val adapterModule = module {
    factory { ProductListAdapter(get()) }
}


