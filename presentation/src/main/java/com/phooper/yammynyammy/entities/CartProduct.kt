package com.phooper.yammynyammy.entities

import com.livermor.delegateadapter.delegate.diff.KDiffUtilItem

data class CartProduct(
    val product: Product,
    val count: Int,
    val totalPrice: Int
) :
    KDiffUtilItem {

    override val id: Any
        get() = product.id

}