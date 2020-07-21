package com.phooper.yammynyammy.di.components

import com.phooper.yammynyammy.di.modules.ProductListModule
import com.phooper.yammynyammy.di.scopes.ProductListScope
import com.phooper.yammynyammy.ui.product_list.ProductListFragment
import dagger.BindsInstance
import dagger.Component

@ProductListScope
@Component(modules = [ProductListModule::class])
interface ProductListComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance productListFragment: ProductListFragment): ProductListComponent
    }

}