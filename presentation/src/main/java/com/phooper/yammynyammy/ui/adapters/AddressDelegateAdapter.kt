package com.phooper.yammynyammy.ui.adapters

import android.annotation.SuppressLint
import com.livermor.delegateadapter.delegate.KDelegateAdapter
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.entities.Address
import kotlinx.android.synthetic.main.item_address.*

data class AddressDelegateAdapter(
    private val onItemClickListener: (String) -> (Unit),
    private val onEditBtnClickListener: (String) -> (Unit)
) :
    KDelegateAdapter<Address>() {
    override fun getLayoutId() = R.layout.item_address

    @SuppressLint("SetTextI18n")
    override fun KViewHolder.onBind(item: Address) {
        address.text = "${R.string.apartment_short} ${item.apartNum} ${item.houseNum} ${item.street}"
        item_layout.setOnClickListener {
            onItemClickListener.invoke(item.id)
        }
        edit_btn.setOnClickListener {
            onEditBtnClickListener.invoke(item.id)
        }

    }

    override fun isForViewType(item: Any): Boolean = item is Address
}

