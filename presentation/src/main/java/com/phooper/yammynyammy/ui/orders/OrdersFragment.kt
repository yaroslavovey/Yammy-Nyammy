package com.phooper.yammynyammy.ui.orders

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.ui.global.adapters.OrderPreviewAdapter
import com.phooper.yammynyammy.ui.global.BaseFragment
import kotlinx.android.synthetic.main.fragment_orders.*
import kotlinx.android.synthetic.main.no_network_layout.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

class OrdersFragment : BaseFragment() {

    override val layoutRes = R.layout.fragment_orders


    private val orderAdapter = OrderPreviewAdapter().apply {
        onItemClick =
            {
//                navController.navigate(OrdersFragmentDirections.actionOrdersFragmentToOrderFragment(it))
            }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        recycler_view.apply {
            adapter = orderAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
//
//        go_back_to_menu_btn.setOnClickListener {
//            navController.navigate(R.id.menu_fragment)
//        }
//
//        viewModel.orderList.observe(viewLifecycleOwner, Observer { ordersList ->
//            orderAdapter.setData(ordersList)
//        })
//
//        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
//            state?.let {
//                when (state) {
//                    OrdersViewModel.ViewState.NO_ORDERS -> {
//                        no_orders_layout.visibility = View.VISIBLE
//                        recycler_view.visibility = View.GONE
//                        progress_bar.visibility = View.GONE
//                        no_network_layout.visibility = View.GONE
//                    }
//                    OrdersViewModel.ViewState.LOADING -> {
//                        no_orders_layout.visibility = View.GONE
//                        recycler_view.visibility = View.GONE
//                        progress_bar.visibility = View.VISIBLE
//                        no_network_layout.visibility = View.GONE
//                    }
//                    OrdersViewModel.ViewState.DEFAULT -> {
//                        no_orders_layout.visibility = View.GONE
//                        recycler_view.visibility = View.VISIBLE
//                        progress_bar.visibility = View.GONE
//                        no_network_layout.visibility = View.GONE
//                    }
//                    OrdersViewModel.ViewState.NETWORK_ERROR -> {
//                        no_orders_layout.visibility = View.GONE
//                        recycler_view.visibility = View.GONE
//                        progress_bar.visibility = View.GONE
//                        no_network_layout.visibility = View.VISIBLE
//                    }
//                }
//            }
//        })
//
//        refresh_btn.setOnClickListener { viewModel.loadOrderList() }

    }
}