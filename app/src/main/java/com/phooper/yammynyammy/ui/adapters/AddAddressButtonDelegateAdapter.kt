package com.phooper.yammynyammy.ui.adapters

import com.livermor.delegateadapter.delegate.KDelegateAdapter
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.data.models.AddAddressButton
import kotlinx.android.synthetic.main.item_add_new_address_btn.*

class AddAddressButtonDelegateAdapter(private val onBtnClickListener: () -> (Unit)) :
    KDelegateAdapter<AddAddressButton>() {

    override fun KViewHolder.onBind(item: AddAddressButton) {
        add_new_address_btn.setOnClickListener { onBtnClickListener.invoke() }
    }

    override fun isForViewType(item: Any): Boolean = item is AddAddressButton

    override fun getLayoutId() = R.layout.item_add_new_address_btn
}