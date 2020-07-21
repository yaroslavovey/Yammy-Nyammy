package com.phooper.yammynyammy.di.modules

import android.content.Context
import com.phooper.yammynyammy.di.scopes.AppScope
import com.phooper.yammynyammy.utils.StringProvider
import com.phooper.yammynyammy.utils.SystemMessageNotifier
import dagger.Module
import dagger.Provides

@Module
class UtilsModule {

    @Provides
    @AppScope
    fun provideGetStringProvider(context: Context): StringProvider = StringProvider(context)

    @Provides
    @AppScope
    fun provideSystemNotifier(context: Context): SystemMessageNotifier =
        SystemMessageNotifier(context)

}