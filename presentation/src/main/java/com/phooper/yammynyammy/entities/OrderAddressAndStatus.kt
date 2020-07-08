package com.phooper.yammynyammy.entities

import com.livermor.delegateadapter.delegate.diff.KDiffUtilItem

data class OrderAddressAndStatus(
    val address: String,
    val status: String
) : KDiffUtilItem {

    override val id: Any
        get() = address + status

}