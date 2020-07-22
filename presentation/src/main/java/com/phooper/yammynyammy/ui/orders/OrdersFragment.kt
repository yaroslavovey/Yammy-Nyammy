package com.phooper.yammynyammy.ui.orders

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.entities.Order
import com.phooper.yammynyammy.presenters.orders.OrdersPresenter
import com.phooper.yammynyammy.presenters.orders.OrdersView
import com.phooper.yammynyammy.ui.global.BaseFragment
import com.phooper.yammynyammy.ui.global.adapters.OrderPreviewAdapter
import kotlinx.android.synthetic.main.fragment_orders.*
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class OrdersFragment : BaseFragment(), OrdersView {

    override val layoutRes = R.layout.fragment_orders

    @Inject
    lateinit var presenterProvider: Provider<OrdersPresenter>

    private val presenter by moxyPresenter { presenterProvider.get() }

    private val orderAdapter = OrderPreviewAdapter().apply {
        onItemClick = {}
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

    override fun showLoading() {
        TODO("Not yet implemented")
    }

    override fun hideLoading() {
        TODO("Not yet implemented")
    }

    override fun showNoNetwork() {
        TODO("Not yet implemented")
    }

    override fun hideNoNetwork() {
        TODO("Not yet implemented")
    }

    override fun showNoOrders() {
        TODO("Not yet implemented")
    }

    override fun hideNoOrders() {
        TODO("Not yet implemented")
    }

    override fun setOrdersList(list: List<Order>) {
        TODO("Not yet implemented")
    }
}