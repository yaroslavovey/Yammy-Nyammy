package com.phooper.yammynyammy.di.modules

import com.phooper.yammynyammy.di.scopes.AppScope
import com.phooper.yammynyammy.utils.Constants.Companion.FLOW
import com.phooper.yammynyammy.utils.Constants.Companion.GLOBAL
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Named

@Module
class NavigationModule {

    /**
     * Global navigation
     * */

    @Provides
    @AppScope
    @Named(GLOBAL)
    fun provideCicerone(): Cicerone<Router> = Cicerone.create()

    @Provides
    @AppScope
    @Named(GLOBAL)
    fun provideRouter(@Named(GLOBAL) cicerone: Cicerone<Router>): Router = cicerone.router

    @Provides
    @AppScope
    @Named(GLOBAL)
    fun provideNavHolder(@Named(GLOBAL) cicerone: Cicerone<Router>): NavigatorHolder =
        cicerone.navigatorHolder

    /**
     * Flow navigation
     * */

    @Provides
    @AppScope
    @Named(FLOW)
    fun provideFlowCicerone(): Cicerone<Router> = Cicerone.create()

    @Provides
    @AppScope
    @Named(FLOW)
    fun provideFlowRouter(@Named(FLOW) cicerone: Cicerone<Router>): Router = cicerone.router

    @Provides
    @AppScope
    @Named(FLOW)
    fun provideFlowNavHolder(@Named(FLOW) cicerone: Cicerone<Router>): NavigatorHolder =
        cicerone.navigatorHolder

}