package com.phooper.yammynyammy.data.models

import com.livermor.delegateadapter.delegate.diff.KDiffUtilItem

data class Order(
    val number: Int,
    val address: Address,
    val date: String,
    val time: String,
    val products: List<Product>
) :
    KDiffUtilItem {
    override val id = number
}