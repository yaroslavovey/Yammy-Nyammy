package com.phooper.yammynyammy.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.utils.Constants.Companion.PRODUCT_ID
import com.phooper.yammynyammy.viewmodels.AddToCartDialogViewModel
import kotlinx.android.synthetic.main.dialog_fragment_add_to_cart.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AddToCartDialogFragment : BottomSheetDialogFragment() {

    private val viewModel by viewModel<AddToCartDialogViewModel> {
        parametersOf(
            requireArguments().getInt(
                PRODUCT_ID
            )
        )
    }

    companion object {
        fun create(productId: Int) = AddToCartDialogFragment().apply {
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
        viewModel.itemCount.observe(this, Observer {
            count_text.text = it.toString()
        })

        close_btn.setOnClickListener {
            dismiss()
        }

        add_to_cart_btn.setOnClickListener {
            viewModel.addProductsToCart()
            Snackbar.make(dlg_constraint_layout, R.string.added_to_cart, Snackbar.LENGTH_SHORT)
                .show()
            dismiss()
        }

        plus_btn.setOnClickListener {
            viewModel.increaseItemCountByOne()
        }

        minus_btn.setOnClickListener {
            viewModel.decreaseItemCountByOne()
        }
    }

}