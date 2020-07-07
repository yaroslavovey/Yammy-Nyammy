package com.phooper.yammynyammy.di

import androidx.room.Room
import com.phooper.yammynyammy.data.db.AppDb
import com.phooper.yammynyammy.utils.Constants
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val roomModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDb::class.java, Constants.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<AppDb>().getCartProductsDao() }
}