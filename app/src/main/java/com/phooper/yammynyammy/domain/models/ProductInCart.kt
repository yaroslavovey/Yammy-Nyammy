package com.phooper.yammynyammy.domain.models

import com.livermor.delegateadapter.delegate.diff.DiffUtilItem

data class ProductInCart(
    val product: Product = Product(),
    val count: Int = 0,
    //Not sure about that...
    val totalPrice: Int = product.price * count
) :
    DiffUtilItem {
    override fun id() = product.id

    override fun content() = this
}