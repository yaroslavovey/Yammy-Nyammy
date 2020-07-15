package com.phooper.yammynyammy.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.entities.Order
import com.phooper.yammynyammy.utils.formatToString
import kotlinx.android.synthetic.main.item_order_preview.view.*

class OrderPreviewAdapter :
    RecyclerView.Adapter<OrderPreviewAdapter.OrderViewHolder>() {
    var dataList = mutableListOf<Order>()
    var onItemClick: ((String) -> Unit)? = null

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(dataList[adapterPosition].id)
            }
        }

        val date: TextView = itemView.order_date
        val status: TextView = itemView.status
        val totalSum: TextView = itemView.total_sum
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        OrderViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_order_preview,
                parent,
                false
            )
        )

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.apply {
            date.text = dataList[position].timestamp?.formatToString()
            status.text = dataList[position].addressAndStatus.status
            totalSum.text = dataList[position].totalPrice.toString()
        }
    }

    fun setData(list: List<Order>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

}