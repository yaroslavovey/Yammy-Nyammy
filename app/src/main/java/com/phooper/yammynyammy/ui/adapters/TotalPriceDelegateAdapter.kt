package com.phooper.yammynyammy.ui.adapters

import android.annotation.SuppressLint
import com.livermor.delegateadapter.delegate.KDelegateAdapter
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.domain.models.TotalAndDeliveryPrice
import kotlinx.android.synthetic.main.item_total_delivery_price.*

class TotalPriceDelegateAdapter : KDelegateAdapter<TotalAndDeliveryPrice>() {

    override fun isForViewType(item: Any): Boolean = item is TotalAndDeliveryPrice

    override fun getLayoutId() = R.layout.item_total_delivery_price

    @SuppressLint("SetTextI18n")
    override fun KViewHolder.onBind(item: TotalAndDeliveryPrice) {
        delivery_price.text = "${item.deliveryPrice} ₽"
        total_price.text = "${item.totalPrice} ₽"
    }
}