package com.ph00.domain.models

import java.util.*

data class OrderModel(
    val uid: String = "",
    val timestamp: Date? = null,
    val addressAndStatus: OrderAddressAndStatusModel = OrderAddressAndStatusModel(),
    val products: List<CartProductModel> = listOf(CartProductModel()),
    val deliveryPrice: Int = 0,
    //Not sure about that...
    val totalPrice: Int = products.sumBy { it.totalPrice } + deliveryPrice
)
