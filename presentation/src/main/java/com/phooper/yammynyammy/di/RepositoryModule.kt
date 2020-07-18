package com.phooper.yammynyammy.di

import com.ph00.data.repositories_impl.AuthRepositoryImpl
import com.ph00.data.repositories_impl.ProductsRepositoryImpl
import com.ph00.data.repositories_impl.UserRepositoryImpl
import com.ph00.domain.repositories.AuthRepository
import com.ph00.domain.repositories.ProductsRepository
import com.ph00.domain.repositories.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

@ExperimentalCoroutinesApi
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