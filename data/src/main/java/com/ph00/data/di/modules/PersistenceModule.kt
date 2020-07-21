package com.ph00.data.di.modules

import android.content.Context
import androidx.room.Room
import com.ph00.data.Constants
import com.ph00.data.db.AppDb
import com.ph00.data.db.dao.CartProductsDao
import com.ph00.data.di.scopes.DataScope
import dagger.Module
import dagger.Provides

@Module
class PersistenceModule {

    @Provides
    @DataScope
    fun provideRoom(context: Context): AppDb =
        Room.databaseBuilder(context, AppDb::class.java, Constants.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @DataScope
    fun provideCartProductsDao(appDb: AppDb): CartProductsDao =
        appDb.getCartProductsDao()
}