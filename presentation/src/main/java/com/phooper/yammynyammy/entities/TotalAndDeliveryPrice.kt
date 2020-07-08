package com.phooper.yammynyammy.entities

import com.livermor.delegateadapter.delegate.diff.KDiffUtilItem

data class TotalAndDeliveryPrice(val deliveryPrice: Int, val totalPrice: Int) : KDiffUtilItem {
    override val id = deliveryPrice
}