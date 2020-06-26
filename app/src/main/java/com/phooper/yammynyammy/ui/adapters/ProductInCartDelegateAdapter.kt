package com.phooper.yammynyammy.ui.adapters

import com.livermor.delegateadapter.delegate.KDelegateAdapter
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.data.models.ProductInCart
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_cart_product.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class ProductInCartDelegateAdapter : KDelegateAdapter<ProductInCart>(), KoinComponent {

    private val picasso by inject<Picasso>()

    override fun isForViewType(item: Any): Boolean = item is ProductInCart

    override fun KViewHolder.onBind(item: ProductInCart) {
        picasso.load(item.product.imageURL).into(product_image)
        product_description.text = item.product.desc
        product_title.text = item.product.title
        product_count.text = item.count.toString()
        product_price.text = item.product.price
    }

    override fun getLayoutId(): Int = R.layout.item_cart_product
}