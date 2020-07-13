package com.phooper.yammynyammy.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.livermor.delegateadapter.delegate.diff.DiffUtilCompositeAdapter
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.ui.adapters.CartProductDelegateAdapter
import com.phooper.yammynyammy.ui.adapters.TotalPriceDelegateAdapter
import com.phooper.yammynyammy.viewmodels.CartViewModel
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.no_network_layout.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@FlowPreview
@ExperimentalCoroutinesApi
class CartFragment : BaseFragment() {

    private val viewModel by viewModel<CartViewModel>()

    override val layoutRes = R.layout.fragment_cart

    private lateinit var navController: NavController

    private val delegateAdapter =
        DiffUtilCompositeAdapter.Builder()
            .add(
                CartProductDelegateAdapter(
                    onPlusClickListener = {
                        viewModel.addOneProductToCart(it)
                    },
                    onMinusClickListener = {
                        viewModel.removeOneProductFromCart(it)
                    })
            ).add(TotalPriceDelegateAdapter())
            .build()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navController = findNavController()
        initViews()
    }

    private fun initViews() {
        make_order_btn.setOnClickListener { navController.navigate(R.id.action_cart_fragment_to_makeOrderFragment) }

        recycler_view.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = delegateAdapter
        }

        viewModel.productsInCart.observe(viewLifecycleOwner, Observer {
            delegateAdapter.swapData(it)
        })

        viewModel.state.observe(viewLifecycleOwner, Observer { viewState ->
            viewState?.let {
                when (it) {
                    CartViewModel.ViewState.LOADING -> {
                        progress_bar.visibility = View.VISIBLE
                        no_products_in_cart_layout.visibility = View.GONE
                        no_network_layout.visibility = View.GONE
                    }
                    CartViewModel.ViewState.DEFAULT -> {
                        progress_bar.visibility = View.GONE
                        recycler_view.visibility = View.VISIBLE
                        make_order_btn.visibility = View.VISIBLE
                        no_products_in_cart_layout.visibility = View.GONE
                        no_network_layout.visibility = View.GONE
                    }
                    CartViewModel.ViewState.NO_PRODUCTS_IN_CART -> {
                        progress_bar.visibility = View.GONE
                        make_order_btn.visibility = View.GONE
                        recycler_view.visibility = View.GONE
                        no_products_in_cart_layout.visibility = View.VISIBLE
                        no_network_layout.visibility = View.GONE
                    }
                    CartViewModel.ViewState.NO_NETWORK -> {
                        progress_bar.visibility = View.GONE
                        make_order_btn.visibility = View.GONE
                        recycler_view.visibility = View.GONE
                        no_products_in_cart_layout.visibility = View.GONE
                        no_network_layout.visibility = View.VISIBLE
                    }
                }
            }
        })

        refresh_btn.setOnClickListener {
            viewModel.getCartProducts()
        }

        go_back_to_menu_btn.setOnClickListener {
            navController.navigate(R.id.menu_fragment)
        }

    }
}