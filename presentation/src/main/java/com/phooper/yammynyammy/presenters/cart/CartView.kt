package com.phooper.yammynyammy.presenters.cart

import com.livermor.delegateadapter.delegate.diff.KDiffUtilItem
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface CartView : MvpView {

    @AddToEndSingle
    fun showLoading()

    @AddToEndSingle
    fun hideLoading()

    @AddToEndSingle
    fun showNoNetwork()

    @AddToEndSingle
    fun hideNoNetwork()

    @AddToEndSingle
    fun showNoProductsInCart()

    @AddToEndSingle
    fun hideNoProductsInCart()

    @AddToEndSingle
    fun showMakeOrderButton()

    @AddToEndSingle
    fun hideMakeOrderButton()

    @AddToEndSingle
    fun setCartProductList(list: List<KDiffUtilItem>)

}