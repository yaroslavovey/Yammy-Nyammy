package com.phooper.yammynyammy.entities

import com.livermor.delegateadapter.delegate.diff.KDiffUtilItem
import java.util.*

data class Order(
    override val id: String,
    val timestamp: Date?,
    val addressAndStatus: OrderAddressAndStatus,
    val products: List<CartProduct>,
    val deliveryPrice: Int,
    val totalPrice: Int
) :
    KDiffUtilItem
