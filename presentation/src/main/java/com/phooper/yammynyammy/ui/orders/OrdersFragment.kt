package com.phooper.yammynyammy.ui.orders

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.entities.Order
import com.phooper.yammynyammy.presenters.orders.OrdersPresenter
import com.phooper.yammynyammy.presenters.orders.OrdersView
import com.phooper.yammynyammy.ui.global.BaseFragment
import com.phooper.yammynyammy.ui.global.adapters.OrderPreviewAdapter
import kotlinx.android.synthetic.main.fragment_orders.*
import kotlinx.android.synthetic.main.no_network_layout.*
import moxy.ktx.moxyPresenter
import timber.log.Timber
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

        go_back_to_menu_btn.setOnClickListener {
            presenter.backToMenu()
        }

        refresh_btn.setOnClickListener { presenter.loadOrders() }

    }

    override fun showLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress_bar.visibility = View.GONE
    }

    override fun showNoNetwork() {
        no_network_layout.visibility = View.VISIBLE
    }

    override fun hideNoNetwork() {
        no_network_layout.visibility = View.GONE
    }

    override fun showNoOrders() {
        no_orders_layout.visibility = View.VISIBLE
    }

    override fun hideNoOrders() {
        no_orders_layout.visibility = View.GONE
    }

    override fun setOrdersList(list: List<Order>) {
        orderAdapter.setData(list)
    }
}