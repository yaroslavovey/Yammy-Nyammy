package com.ph00.data.di.components

import android.app.Application
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ph00.data.api.ShopApi
import com.ph00.data.db.dao.CartProductsDao
import com.ph00.data.di.modules.ApiModule
import com.ph00.data.di.modules.NetworkModule
import com.ph00.data.di.modules.PersistenceModule
import com.ph00.data.di.modules.RepositoryModule
import com.ph00.data.di.scopes.DataScope
import com.ph00.domain.repositories.AuthRepository
import com.ph00.domain.repositories.ProductsRepository
import com.ph00.domain.repositories.UserRepository
import dagger.BindsInstance
import dagger.Component

@Component(modules = [ApiModule::class, NetworkModule::class, PersistenceModule::class, RepositoryModule::class])
@DataScope
interface DataComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): DataComponent
    }

    fun getAuthRepository(): AuthRepository
    fun getProductsRepository(): ProductsRepository
    fun getUserRepository(): UserRepository

}