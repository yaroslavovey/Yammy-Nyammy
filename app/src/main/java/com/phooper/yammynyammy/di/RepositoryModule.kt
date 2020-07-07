package com.phooper.yammynyammy.di

import com.phooper.yammynyammy.data.repositories_impl.AuthRepositoryImpl
import com.phooper.yammynyammy.data.repositories_impl.ProductsRepositoryImpl
import com.phooper.yammynyammy.data.repositories_impl.UserRepositoryImpl
import com.phooper.yammynyammy.domain.repositories.AuthRepository
import com.phooper.yammynyammy.domain.repositories.ProductsRepository
import com.phooper.yammynyammy.domain.repositories.UserRepository
import org.koin.dsl.module

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