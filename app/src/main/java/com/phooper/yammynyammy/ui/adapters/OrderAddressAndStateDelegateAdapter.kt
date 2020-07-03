package com.phooper.yammynyammy.ui.adapters

import com.livermor.delegateadapter.delegate.KDelegateAdapter
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.domain.models.OrderAddressAndStatus
import kotlinx.android.synthetic.main.item_order_address_status.*

class OrderAddressAndStateDelegateAdapter() :
    KDelegateAdapter<OrderAddressAndStatus>() {

    override fun isForViewType(item: Any): Boolean = item is OrderAddressAndStatus

    override fun KViewHolder.onBind(item: OrderAddressAndStatus) {
        status.text = item.status
        order_address.text = item.address
    }

    override fun getLayoutId(): Int = R.layout.item_order_address_status
}