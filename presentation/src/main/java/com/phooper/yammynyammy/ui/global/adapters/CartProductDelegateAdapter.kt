package com.phooper.yammynyammy.ui.global.adapters

import com.livermor.delegateadapter.delegate.KDelegateAdapter
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.entities.CartProduct
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_cart_product.*
import javax.inject.Inject

class CartProductDelegateAdapter @Inject constructor(private val picasso: Picasso) :
    KDelegateAdapter<CartProduct>() {

    var onPlusClickListener: ((Int) -> Unit)? = null
    var onMinusClickListener: ((Int) -> Unit)? = null

    override fun isForViewType(item: Any): Boolean = item is CartProduct

    override fun KViewHolder.onBind(item: CartProduct) {
        picasso.load(item.product.imageURL).into(product_image)
        product_description.text = item.product.desc
        product_title.text = item.product.title
        product_count.text = item.count.toString()
        product_price.text = item.totalPrice.toString()
        minus_btn.setOnClickListener { onMinusClickListener?.invoke(item.product.id) }
        plus_btn.setOnClickListener { onPlusClickListener?.invoke(item.product.id) }
    }

    override fun getLayoutId(): Int = R.layout.item_cart_product
}