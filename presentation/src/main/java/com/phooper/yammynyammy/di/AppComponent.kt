package com.phooper.yammynyammy.di

import android.content.Context
import com.ph00.data.di.components.DataComponent
import com.phooper.yammynyammy.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@AppScope
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AndroidInjectionModule::class,
        NavigationModule::class,
        ScreenBindingModule::class,
        UtilsModule::class],
    dependencies = [DataComponent::class]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance applicationContext: Context,
            dataComponent: DataComponent
        ): AppComponent
    }

}