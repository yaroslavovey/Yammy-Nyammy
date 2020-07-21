package com.phooper.yammynyammy.di.modules

import com.phooper.yammynyammy.ui.bottom_nav.BottomNavFlowFragment
import com.phooper.yammynyammy.ui.MainContainerActivity
import com.phooper.yammynyammy.ui.cart.CartFragment
import com.phooper.yammynyammy.ui.login.LoginFlowFragment
import com.phooper.yammynyammy.ui.login.LoginFragment
import com.phooper.yammynyammy.ui.register.RegisterFragment
import com.phooper.yammynyammy.ui.menu.MenuFragment
import com.phooper.yammynyammy.ui.orders.OrdersFragment
import com.phooper.yammynyammy.ui.product_list.ProductListFragment
import com.phooper.yammynyammy.ui.profile.ProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ScreenBindingModule {

    @ContributesAndroidInjector
    abstract fun mainContainerActivity(): MainContainerActivity

    @ContributesAndroidInjector
    abstract fun loginFlowFragment(): LoginFlowFragment

    @ContributesAndroidInjector
    abstract fun loginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun registerFragment(): RegisterFragment

    @ContributesAndroidInjector
    abstract fun bottomNavFlowFragment(): BottomNavFlowFragment

    @ContributesAndroidInjector
    abstract fun menuFragment(): MenuFragment

    @ContributesAndroidInjector
    abstract fun ordersFragment(): OrdersFragment

    @ContributesAndroidInjector
    abstract fun cartFragment(): CartFragment

    @ContributesAndroidInjector
    abstract fun profileFragment(): ProfileFragment

    @ContributesAndroidInjector(modules = [ProductListModule::class])
    abstract fun productListFragment(): ProductListFragment

}