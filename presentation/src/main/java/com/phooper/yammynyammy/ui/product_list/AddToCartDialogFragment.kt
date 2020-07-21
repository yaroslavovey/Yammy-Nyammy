package com.phooper.yammynyammy.ui.product_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.utils.Constants.Companion.PRODUCT_ID
import kotlinx.android.synthetic.main.dialog_fragment_add_to_cart.*
import moxy.MvpBottomSheetDialogFragment

class AddToCartDialogFragment : MvpBottomSheetDialogFragment() {


    companion object {
        fun create(productId: Int) = AddToCartDialogFragment()
            .apply {
                arguments = Bundle().apply { putInt(PRODUCT_ID, productId) }
            }
    }

    override fun onStart() {
        super.onStart()
        val behavior = BottomSheetBehavior.from(requireView().parent as View)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.dialog_fragment_add_to_cart, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
//        viewModel.itemCount.observe(viewLifecycleOwner, Observer {
//            count_text.text = it
//        })
//
//        viewModel.event.observe(viewLifecycleOwner, Observer {
//            it.getContentIfNotHandled()?.let { event ->
//                when (event) {
//                    AddToCartDialogViewModel.ViewEvent.SUCCESS -> {
//                        sharedViewModel.triggerAddedToCartEvent()
//                        dismiss()
//                    }
//                    AddToCartDialogViewModel.ViewEvent.FAILURE -> {
//
//                    }
//                }
//            }
//        })
//
        close_btn.setOnClickListener {
            dismiss()
        }
//
//        add_to_cart_btn.setOnClickListener {
//            viewModel.addProductsToCart()
//        }
//
//        plus_btn.setOnClickListener {
//            viewModel.increaseItemCountByOne()
//        }
//
//        minus_btn.setOnClickListener {
//            viewModel.decreaseItemCountByOne()
//        }
    }

}