package com.phooper.yammynyammy.di.modules

import com.phooper.yammynyammy.ui.product_list.ProductListFragment
import com.phooper.yammynyammy.utils.Constants
import com.phooper.yammynyammy.utils.Constants.Companion.ARG_OBJECT
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class ProductListModule {

    @Provides
    @Named(Constants.CATEGORY)
    fun provideCategory(productListFragment: ProductListFragment) =
        productListFragment.requireArguments().getInt(ARG_OBJECT)

}