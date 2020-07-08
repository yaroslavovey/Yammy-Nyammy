package com.phooper.yammynyammy.ui.adapters

import android.annotation.SuppressLint
import com.livermor.delegateadapter.delegate.KDelegateAdapter
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.entities.CartProduct
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_order_product.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class OrderProductDelegateAdapter() :
    KDelegateAdapter<CartProduct>(), KoinComponent {

    private val picasso by inject<Picasso>()

    override fun isForViewType(item: Any): Boolean = item is CartProduct

    @SuppressLint("SetTextI18n")
    override fun KViewHolder.onBind(item: CartProduct) {
        picasso.load(item.product.imageURL).into(product_image)
        product_description.text = item.product.desc
        product_title.text = item.product.title
        product_count.text = "x${item.count}"
        product_price.text = "${item.totalPrice}  ₽"
    }

    override fun getLayoutId(): Int = R.layout.item_order_product
}