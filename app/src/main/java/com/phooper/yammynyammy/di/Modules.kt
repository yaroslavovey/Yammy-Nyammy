package com.phooper.yammynyammy.di

import androidx.room.Room
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.data.api.ShopApi
import com.phooper.yammynyammy.data.db.AppDb
import com.phooper.yammynyammy.data.repositories_impl.AuthRepositoryImpl
import com.phooper.yammynyammy.data.repositories_impl.ProductsRepositoryImpl
import com.phooper.yammynyammy.data.repositories_impl.UserRepositoryImpl
import com.phooper.yammynyammy.domain.repositories.AuthRepository
import com.phooper.yammynyammy.domain.repositories.ProductsRepository
import com.phooper.yammynyammy.domain.repositories.UserRepository
import com.phooper.yammynyammy.domain.usecases.*
import com.phooper.yammynyammy.ui.adapters.ProductAdapter
import com.phooper.yammynyammy.utils.Constants.Companion.BASE_URL
import com.phooper.yammynyammy.utils.Constants.Companion.DATABASE_NAME
import com.phooper.yammynyammy.viewmodels.*
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
    single<UserRepository> {
        UserRepositoryImpl(firebaseFirestone = get(), cartProductsDao = get())
    }
    single<ProductsRepository> {
        ProductsRepositoryImpl(shopApi = get())
    }
    single<AuthRepository> {
        AuthRepositoryImpl(firebaseAuth = get())
    }
}

val useCaseModule = module {
    //Addresses
    single { AddAddressUseCase(get(), get()) }
    single { DeleteAddressByUidUseCase(get(), get()) }
    single { GetAddressByUidUseCase(get(), get()) }
    single { GetAddressesAsCollectionUseCase(get(), get()) }
    single { UpdateAddressByUidUseCase(get(), get()) }

    //Orders
    single { AddOrderUseCase(get(), get()) }
    single { GetOrderListUseCase(get(), get()) }
    single { GetOrderByUidUseCase(get(), get()) }

    //Cart
    single { AddProductsToCartUseCase(get()) }
    single { RemoveProductsFromCartUseCase(get()) }
    single { GetAllCartProductsCountLiveDataUseCase(get()) }
    single { DropCartUseCase(get()) }

    single { GetAllProductInCartUseCase(get(), get()) }
    single { GetAllProductInCartLiveDataUseCase(get(), get()) }

    single { GetAllCartProductIdAndCountUseCase(get()) }
    single { GetAllCartProductIdAndCountLiveDataUseCase(get()) }


    //Products
    single { GetProductByIdUseCase(get()) }
    single { GetProductListByCategoryUseCase(get()) }
    single { GetProductListByIdsUseCase(get()) }

    //User's phone & name
    single { GetUserDataUseCase(get(), get()) }
    single { SetUserDataUseCase(get(), get()) }
    single { GetUserDataAsDocumentUseCase(get(), get()) }

    //Auth
    single { GetCurrentUserUseCase(get()) }
    single { SignInViaEmailAndPasswordUseCase(get()) }
    single { SignInViaGoogleUseCase(get()) }
    single { SignOutUseCase(get()) }
    single { SignUpViaEmailAndPasswordUseCase(get()) }
    single { UpdateUserPasswordUseCase(get()) }
    single { ReauthenticateUseCase(get(), get()) }

}

val viewModelModule = module {
    viewModel { LoginViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { (category: Int?) -> ProductListViewModel(category, get()) }
    viewModel { (productId: Int) -> AddToCartDialogViewModel(productId, get()) }
    viewModel { (productId: Int) -> ProductViewModel(productId, get(), get()) }
    viewModel { CartViewModel(get(), get(), get(), get()) }
    viewModel { MakeOrderViewModel(get(), get(), get(), get(), get()) }
    viewModel { (choosingAddressForDelivery: Boolean) ->
        MyAddressesViewModel(
            get(),
            choosingAddressForDelivery
        )
    }
    viewModel { OrdersViewModel(get()) }
    viewModel { (addressUid: String?) ->
        AddUpdateAddressViewModel(addressUid, get(), get(), get(), get())
    }
    viewModel { (orderUid: String?) -> OrderViewModel(get(), orderUid) }
    viewModel { MainContainerViewModel(get(), get(), get()) }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { EditProfileViewModel(get(), get()) }
    viewModel { UpdatePasswordViewModel(get(), get()) }
}

val roomModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDb::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<AppDb>().getCartProductsDao() }
}

val adapterModule = module {
    factory { ProductAdapter(get()) }
}


