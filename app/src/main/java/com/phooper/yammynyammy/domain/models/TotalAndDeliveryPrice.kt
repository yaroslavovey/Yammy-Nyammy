package com.phooper.yammynyammy.domain.models

import com.livermor.delegateadapter.delegate.diff.KDiffUtilItem

data class TotalAndDeliveryPrice(val deliveryPrice: Int, val totalPrice: Int) : KDiffUtilItem {
    override val id = deliveryPrice
}