package com.phooper.yammynyammy.presenters.orders

import com.phooper.yammynyammy.entities.Order
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface OrdersView : MvpView {

    @AddToEndSingle
    fun showLoading()

    @AddToEndSingle
    fun hideLoading()

    @AddToEndSingle
    fun showNoNetwork()

    @AddToEndSingle
    fun hideNoNetwork()

    @AddToEndSingle
    fun showNoOrders()

    @AddToEndSingle
    fun hideNoOrders()

    @AddToEndSingle
    fun setOrdersList(list: List<Order>)

}