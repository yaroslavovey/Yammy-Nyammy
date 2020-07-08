package com.ph00.domain.models

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class OrderModel(
    @DocumentId val uid: String = "",
    @ServerTimestamp val timestamp: Date? = null,
    val addressAndStatus: OrderAddressAndStatusModel = OrderAddressAndStatusModel(),
    val products: List<CartProductModel> = listOf(CartProductModel()),
    val deliveryPrice: Int = 0,
    //Not sure about that...
    val totalPrice: Int = products.sumBy { it.totalPrice } + deliveryPrice
)
