package com.phooper.yammynyammy.ui.global.adapters

import com.livermor.delegateadapter.delegate.KDelegateAdapter
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.entities.TotalAndDeliveryPrice
import kotlinx.android.synthetic.main.item_total_delivery_price.*

class TotalPriceDelegateAdapter : KDelegateAdapter<TotalAndDeliveryPrice>() {

    override fun isForViewType(item: Any): Boolean = item is TotalAndDeliveryPrice

    override fun getLayoutId() = R.layout.item_total_delivery_price

    override fun KViewHolder.onBind(item: TotalAndDeliveryPrice) {
        delivery_price.text = item.deliveryPrice.toString()
        total_price.text = item.totalPrice.toString()
    }
}