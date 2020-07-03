package com.phooper.yammynyammy.domain.models

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import com.livermor.delegateadapter.delegate.diff.DiffUtilItem
import com.phooper.yammynyammy.utils.Constants.Companion.DELIVERY_PRICE
import com.phooper.yammynyammy.utils.Constants.Companion.ORDER_STATUS_CLOSED
import java.util.*

data class Order(
    @DocumentId val uid: String = "",
    @ServerTimestamp val timestamp: Date? = null,
    val address: String = "",
    val status: String = ORDER_STATUS_CLOSED,
    val products: List<ProductInCart> = listOf(ProductInCart()),
    val deliveryPrice: Int = DELIVERY_PRICE,
    //Not sure about that...
    val totalPrice: Int = products.sumBy { it.totalPrice } + deliveryPrice
) :
    DiffUtilItem {

    @Exclude
    override fun id(): Any {
        return uid
    }

    @Exclude
    override fun content() = this
}
