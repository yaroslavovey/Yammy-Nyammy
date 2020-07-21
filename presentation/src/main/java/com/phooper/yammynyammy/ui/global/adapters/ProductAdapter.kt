package com.phooper.yammynyammy.ui.global.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.entities.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_product.view.*
import javax.inject.Inject

class ProductAdapter @Inject constructor(private val picasso: Picasso) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    var dataList = mutableListOf<Product>()
    var onItemClick: ((Int) -> Unit)? = null
    var onAddToCartBtnClick: ((Int) -> Unit)? = null

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(dataList[adapterPosition].id)
            }
            itemView.add_to_cart_btn.setOnClickListener {
                onAddToCartBtnClick?.invoke(dataList[adapterPosition].id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ProductViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_product,
                parent,
                false
            )
        )

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.apply {
            itemView.product_title.text = dataList[position].title
            itemView.product_description.text = dataList[position].desc
            itemView.product_price.text = dataList[position].price.toString()
            picasso.load(dataList[position].imageURL).into(itemView.product_image)
        }
    }

    fun setData(list: List<Product>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

}