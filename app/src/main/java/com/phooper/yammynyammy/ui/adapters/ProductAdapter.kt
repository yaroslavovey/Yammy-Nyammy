package com.phooper.yammynyammy.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.domain.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_product.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class ProductAdapter :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(), KoinComponent {
    private val picasso by inject<Picasso>()
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

        val title: TextView = itemView.product_title
        val description: TextView = itemView.product_description
        val image: ImageView = itemView.product_image
        val price: TextView = itemView.product_price
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
            title.text = dataList[position].title
            description.text = dataList[position].desc
            price.text = "${dataList[position].price} ₽"
            picasso.load(dataList[position].imageURL).into(image)
        }
    }

    fun setData(list: List<Product>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

}