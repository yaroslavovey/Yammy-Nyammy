package com.phooper.yammynyammy.ui.cart

import android.os.Bundle
import com.livermor.delegateadapter.delegate.diff.DiffUtilCompositeAdapter
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.ui.global.BaseFragment
import com.phooper.yammynyammy.ui.global.adapters.CartProductDelegateAdapter
import com.phooper.yammynyammy.ui.global.adapters.TotalPriceDelegateAdapter

class CartFragment : BaseFragment() {

    override val layoutRes = R.layout.fragment_cart

    private val delegateAdapter =
        DiffUtilCompositeAdapter.Builder()
            .add(
                CartProductDelegateAdapter(
                    onPlusClickListener = {
//                        viewModel.addOneProductToCart(it)
                    },
                    onMinusClickListener = {
//                        viewModel.removeOneProductFromCart(it)
                    })
            ).add(TotalPriceDelegateAdapter())
            .build()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        initViews()
    }

//    private fun initViews() {
//        make_order_btn.setOnClickListener { navController.navigate(R.id.action_cart_fragment_to_makeOrderFragment) }
//
//        recycler_view.apply {
//            layoutManager = LinearLayoutManager(requireContext())
//            adapter = delegateAdapter
//        }
//
//        viewModel.productsInCart.observe(viewLifecycleOwner, Observer {
//            delegateAdapter.swapData(it)
//        })
//
//        viewModel.state.observe(viewLifecycleOwner, Observer { viewState ->
//            viewState?.let {
//                when (it) {
//                    CartViewModel.ViewState.LOADING -> {
//                        progress_bar.visibility = View.VISIBLE
//                        no_products_in_cart_layout.visibility = View.GONE
//                        no_network_layout.visibility = View.GONE
//                    }
//                    CartViewModel.ViewState.DEFAULT -> {
//                        progress_bar.visibility = View.GONE
//                        recycler_view.visibility = View.VISIBLE
//                        make_order_btn.visibility = View.VISIBLE
//                        no_products_in_cart_layout.visibility = View.GONE
//                        no_network_layout.visibility = View.GONE
//                    }
//                    CartViewModel.ViewState.NO_PRODUCTS_IN_CART -> {
//                        progress_bar.visibility = View.GONE
//                        make_order_btn.visibility = View.GONE
//                        recycler_view.visibility = View.GONE
//                        no_products_in_cart_layout.visibility = View.VISIBLE
//                        no_network_layout.visibility = View.GONE
//                    }
//                    CartViewModel.ViewState.NO_NETWORK -> {
//                        progress_bar.visibility = View.GONE
//                        make_order_btn.visibility = View.GONE
//                        recycler_view.visibility = View.GONE
//                        no_products_in_cart_layout.visibility = View.GONE
//                        no_network_layout.visibility = View.VISIBLE
//                    }
//                }
//            }
//        })
//
//        refresh_btn.setOnClickListener {
//            viewModel.getCartProducts()
//        }
//
//        go_back_to_menu_btn.setOnClickListener {
//            navController.navigate(R.id.menu_fragment)
//        }}

}