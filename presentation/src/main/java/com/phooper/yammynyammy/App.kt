package com.phooper.yammynyammy

import android.app.Application
import com.ph00.data.di.components.DaggerDataComponent
import com.phooper.yammynyammy.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import timber.log.Timber
import javax.inject.Inject

class App : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onCreate() {
        super.onCreate()

        val dataComponent =
            DaggerDataComponent
                .factory()
                .create(this)

        val appComponent = DaggerAppComponent
            .factory()
            .create(this, dataComponent)

        appComponent.inject(this)

        RxJavaPlugins.setErrorHandler {}

        Timber.plant(Timber.DebugTree())
    }
}