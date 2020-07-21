package com.phooper.yammynyammy.ui.product

import android.os.Bundle
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.ui.global.BaseFragment

class ProductFragment : BaseFragment() {

    override val layoutRes = R.layout.fragment_product

//    private val picasso by inject<Picasso>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
//
//        viewModel.totalPrice.observe(viewLifecycleOwner, Observer {
//            product_price.text = it
//        })
//
//        viewModel.itemCount.observe(viewLifecycleOwner, Observer {
//            count_text.text = it
//        })
//
//        viewModel.product.observe(viewLifecycleOwner, Observer { product ->
//            setAppBarTitle(product.title)
//            picasso.load(product.imageURL).into(product_image)
//            product_description.text = product.desc
//        })
//
//        viewModel.state.observe(viewLifecycleOwner, Observer { viewState ->
//            viewState?.let {
//                when (it) {
//                    ProductViewModel.ViewState.LOADING -> {
//                        progress_bar.visibility = View.VISIBLE
//                        product_constraint_layout.visibility = View.GONE
//                        no_network_layout.visibility = View.GONE
//                    }
//                    ProductViewModel.ViewState.DEFAULT -> {
//                        progress_bar.visibility = View.GONE
//                        product_constraint_layout.visibility = View.VISIBLE
//                        no_network_layout.visibility = View.GONE
//                    }
//                    ProductViewModel.ViewState.NETWORK_ERROR -> {
//                        progress_bar.visibility = View.GONE
//                        product_constraint_layout.visibility = View.GONE
//                        no_network_layout.visibility = View.VISIBLE
//                    }
//                }
//            }
//        })
//
//        add_to_cart_btn.setOnClickListener {
//            viewModel.addProductsToCart()
//            sharedViewModel.triggerAddedToCartEvent()
//            navController.popBackStack()
//        }
//
//        plus_btn.setOnClickListener {
//            viewModel.increaseItemCountByOne()
//        }
//
//        minus_btn.setOnClickListener {
//            viewModel.decreaseItemCountByOne()
//        }
//
//        refresh_btn.setOnClickListener { viewModel.loadProduct() }

    }

    companion object {
        private const val ARG_PRODUCT_ID = "arg_product_id"

        fun create(productId: Int) =
            ProductFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PRODUCT_ID, productId)
                }
            }
    }
}