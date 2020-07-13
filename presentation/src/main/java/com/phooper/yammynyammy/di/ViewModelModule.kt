package com.phooper.yammynyammy.di

import com.phooper.yammynyammy.viewmodels.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@FlowPreview
@ExperimentalCoroutinesApi
val viewModelModule = module {
    viewModel { LoginViewModel(get(), get(), get()) }
    viewModel { (category: Int?) -> ProductListViewModel(category, get()) }
    viewModel { (productId: Int) -> AddToCartDialogViewModel(productId, get()) }
    viewModel { (productId: Int) -> ProductViewModel(productId, get(), get()) }
    viewModel { CartViewModel(get(), get(), get(), get()) }
    viewModel { MakeOrderViewModel(get(), get(), get()) }
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