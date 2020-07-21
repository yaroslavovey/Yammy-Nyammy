package com.ph00.data.di.modules

import com.google.firebase.auth.FirebaseAuth
import com.ph00.data.di.scopes.DataScope
import com.ph00.data.repositories_impl.AuthRepositoryImpl
import com.ph00.data.repositories_impl.ProductsRepositoryImpl
import com.ph00.data.repositories_impl.UserRepositoryImpl
import com.ph00.domain.repositories.AuthRepository
import com.ph00.domain.repositories.ProductsRepository
import com.ph00.domain.repositories.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class RepositoryModule {

    @Binds
    @DataScope
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @DataScope
    abstract fun bindProductRepository(productsRepositoryImpl: ProductsRepositoryImpl): ProductsRepository

    @Binds
    @DataScope
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

}