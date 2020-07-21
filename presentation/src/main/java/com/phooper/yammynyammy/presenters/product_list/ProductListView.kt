package com.phooper.yammynyammy.presenters.product_list

import com.phooper.yammynyammy.entities.Product
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface ProductListView : MvpView {

    @AddToEndSingle
    fun showLoading()

    @AddToEndSingle
    fun hideLoading()

    @AddToEndSingle
    fun showNoNetwork()

    @AddToEndSingle
    fun hideNoNetwork()

    @AddToEndSingle
    fun setProductList(list: List<Product>)

}