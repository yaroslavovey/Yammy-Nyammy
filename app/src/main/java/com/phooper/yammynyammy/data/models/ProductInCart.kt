package com.phooper.yammynyammy.data.models

import com.livermor.delegateadapter.delegate.diff.KDiffUtilItem

data class ProductInCart(val product: Product, val count: Int, val totalPrice : Int) : KDiffUtilItem {
    override val id = product.id
}