package com.phooper.yammynyammy.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.utils.Constants.Companion.PRODUCT_ID
import com.phooper.yammynyammy.viewmodels.AddToCartDialogViewModel
import com.phooper.yammynyammy.viewmodels.MainContainerViewModel
import kotlinx.android.synthetic.main.dialog_fragment_add_to_cart.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

@FlowPreview
@ExperimentalCoroutinesApi
class AddToCartDialogFragment : BottomSheetDialogFragment() {

    private lateinit var navController: NavController

    private val sharedViewModel by sharedViewModel<MainContainerViewModel>()

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
        navController = findNavController()
        initViews()
    }

    private fun initViews() {
        viewModel.itemCount.observe(viewLifecycleOwner, Observer {
            count_text.text = it
        })

        viewModel.event.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { event ->
                when (event) {
                    AddToCartDialogViewModel.ViewEvent.SUCCESS -> {
                        sharedViewModel.triggerAddedToCartEvent()
                        dismiss()
                    }
                    AddToCartDialogViewModel.ViewEvent.FAILURE -> {

                    }
                }
            }
        })

        close_btn.setOnClickListener {
            dismiss()
        }

        add_to_cart_btn.setOnClickListener {
            viewModel.addProductsToCart()
        }

        plus_btn.setOnClickListener {
            viewModel.increaseItemCountByOne()
        }

        minus_btn.setOnClickListener {
            viewModel.decreaseItemCountByOne()
        }
    }

}