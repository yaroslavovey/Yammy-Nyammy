package com.phooper.yammynyammy.di

import com.phooper.yammynyammy.data.api.ShopApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single { get<Retrofit>().create(ShopApi::class.java) }
}