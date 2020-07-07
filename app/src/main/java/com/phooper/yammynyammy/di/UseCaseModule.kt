package com.phooper.yammynyammy.di

import com.phooper.yammynyammy.domain.usecases.*
import org.koin.dsl.module

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