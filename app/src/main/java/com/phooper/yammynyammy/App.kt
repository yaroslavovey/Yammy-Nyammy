package com.phooper.yammynyammy

import android.app.Application
import com.phooper.yammynyammy.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                appComponent
            )
        }
        Timber.plant(Timber.DebugTree())
    }
}