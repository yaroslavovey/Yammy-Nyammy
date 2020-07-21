package com.ph00.data.di.modules

import com.ph00.data.api.ShopApi
import com.ph00.data.di.scopes.DataScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ApiModule {

    @Provides
    @DataScope
    fun provideShopApi(retrofit: Retrofit): ShopApi =
        retrofit.create(ShopApi::class.java)

}