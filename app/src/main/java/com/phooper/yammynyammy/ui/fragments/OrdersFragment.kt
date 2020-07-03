package com.phooper.yammynyammy.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.ui.adapters.OrderPreviewAdapter
import com.phooper.yammynyammy.viewmodels.OrdersViewModel
import kotlinx.android.synthetic.main.fragment_orders.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrdersFragment : BaseFragment() {

    override val layoutRes = R.layout.fragment_orders

    private lateinit var navController: NavController

    private val viewModel by viewModel<OrdersViewModel>()

    private val orderAdapter = OrderPreviewAdapter().apply {
        onItemClick =
            { navController.navigate(OrdersFragmentDirections.actionOrdersFragmentToOrderFragment(it)) }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navController = findNavController()
        initViews()
    }

    private fun initViews() {
        recycler_view.apply {
            adapter = orderAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        go_back_to_menu_btn.setOnClickListener {
            navController.navigate(R.id.menu_fragment)
        }

        viewModel.orderList.observe(viewLifecycleOwner, Observer { ordersList ->
            orderAdapter.setData(ordersList)
        })

        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            state?.let {
                when (state) {
                    OrdersViewModel.ViewState.NO_ORDERS -> {
                        no_orders_layout.visibility = View.VISIBLE
                        recycler_view.visibility = View.GONE
                        progress_bar.visibility = View.GONE
                    }
                    OrdersViewModel.ViewState.LOADING -> {
                        progress_bar.visibility = View.VISIBLE
                    }
                    OrdersViewModel.ViewState.DEFAULT -> {
                        no_orders_layout.visibility = View.GONE
                        recycler_view.visibility = View.VISIBLE
                        progress_bar.visibility = View.GONE
                    }
                }
            }
        })

    }
}