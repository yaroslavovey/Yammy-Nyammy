package com.phooper.yammynyammy.di

import com.ph00.domain.usecases.*
import org.koin.dsl.module

val useCaseModule = module {
    //Addresses
    single { AddAddressUseCase(get(), get()) }
    single { DeleteAddressByUidUseCase(get(), get()) }
    single { GetAddressByUidUseCase(get(), get()) }
    single { UpdateAddressByUidUseCase(get(), get()) }
    single { GetAllAddressesAsFlowUseCase(get(), get()) }

    //Orders
    single { AddOrderUseCase(get(), get()) }
    single { GetOrderListUseCase(get(), get()) }
    single { GetOrderByUidUseCase(get(), get()) }
    single { GetDeliveryPriceUseCase(get()) }

    //Cart
    single { AddProductsToCartUseCase(get()) }
    single { RemoveProductsFromCartUseCase(get()) }
    single { GetAllCartProductCountUseCase(get()) }
    single { DropCartUseCase(get()) }

    single { GetAllProductInCartUseCase(get(), get()) }
    single { GetAllProductInCartAsFlowUseCase(get(), get()) }

    single { GetAllCartProductIdAndCountUseCase(get()) }
    single { GetAllCartProductIdAndCountAsFLowUseCase(get()) }


    //Products
    single { GetProductByIdUseCase(get()) }
    single { GetProductListByCategoryUseCase(get()) }
    single { GetProductListByIdsUseCase(get()) }

    //User's phone & name
    single { GetUserDataUseCase(get(), get()) }
    single { GetUserDataAsFlowUseCase(get(), get()) }
    single { SetUserDataUseCase(get(), get(), get()) }

    //Auth
    single { GetCurrentUserUidUseCase(get()) }
    single { GetCurrentUserEmailUseCase(get()) }
    single { SignInViaEmailAndPasswordUseCase(get()) }
    single { SignOutUseCase(get()) }
    single { SignUpViaEmailAndPasswordUseCase(get()) }
    single { UpdateUserPasswordUseCase(get()) }
    single { ReauthenticateUseCase(get(), get()) }
//    single { SignInAnonymouslyUseCase(get()) }
//    single { LinkCurrentUserWithCredentialUseCase(get(), get()) }
//    single { CurrentUserIsAnonymousUseCase(get()) }

}