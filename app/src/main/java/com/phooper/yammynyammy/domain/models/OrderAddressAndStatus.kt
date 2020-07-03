package com.phooper.yammynyammy.domain.models

import com.google.firebase.firestore.Exclude
import com.livermor.delegateadapter.delegate.diff.DiffUtilItem
import com.phooper.yammynyammy.utils.Constants

data class OrderAddressAndStatus(
    val address: String = "",
    val status: String = Constants.ORDER_STATUS_CLOSED
) : DiffUtilItem {

    @Exclude
    override fun id() = address + status

    @Exclude
    override fun content() = this
}