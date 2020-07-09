package com.ph00.data.entities

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import com.ph00.domain.models.CartProductModel
import com.ph00.domain.models.OrderAddressAndStatusModel
import java.util.*

data class OrderEntity(
    @DocumentId val uid: String = "",
    @ServerTimestamp val timestamp: Date? = null,
    val addressAndStatus: OrderAddressAndStatusModel = OrderAddressAndStatusModel(),
    val products: List<CartProductModel> = listOf(CartProductModel()),
    val deliveryPrice: Int = 0,
    val totalPrice: Int = 0
)
