package com.phooper.yammynyammy.ui.order

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.livermor.delegateadapter.delegate.diff.DiffUtilCompositeAdapter
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.ui.global.BaseFragment
import com.phooper.yammynyammy.ui.global.adapters.OrderAddressAndStateDelegateAdapter
import com.phooper.yammynyammy.ui.global.adapters.OrderProductDelegateAdapter
import com.phooper.yammynyammy.ui.global.adapters.TotalPriceDelegateAdapter
import kotlinx.android.synthetic.main.fragment_order.*

class OrderFragment : BaseFragment() {

    override val layoutRes = R.layout.fragment_order

    private val diffAdapter =
        DiffUtilCompositeAdapter.Builder()
            .add(OrderAddressAndStateDelegateAdapter())
            .add(OrderProductDelegateAdapter())
            .add(TotalPriceDelegateAdapter())
            .build()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = diffAdapter
        }
//
//        viewModel.appBarTitleDate.observe(viewLifecycleOwner, Observer { date ->
//            setAppBarTitle(getString(R.string.order_date) + date)
//        })
//
//        viewModel.orderInfo.observe(viewLifecycleOwner, Observer {
//            diffAdapter.swapData(it)
//        })
//
//        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
//            state?.let {
//                when (it) {
//                    OrderViewModel.ViewState.LOADING -> {
//                        progress_bar.visibility = View.VISIBLE
//                        recycler_view.visibility = View.VISIBLE
//                        no_network_layout.visibility = View.GONE
//                    }
//                    OrderViewModel.ViewState.DEFAULT -> {
//                        progress_bar.visibility = View.GONE
//                        recycler_view.visibility = View.VISIBLE
//                        no_network_layout.visibility = View.GONE
//                    }
//                    OrderViewModel.ViewState.NETWORK_ERROR -> {
//                        progress_bar.visibility = View.GONE
//                        recycler_view.visibility = View.GONE
//                        no_network_layout.visibility = View.VISIBLE
//                    }
//                }
//            }
//        })
//
//        refresh_btn.setOnClickListener { viewModel.loadOrder() }

    }

    companion object {
        private const val ARG_ORDER_ID = "arg_order_id"

        fun create(orderId: String) =
            OrderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ORDER_ID, orderId)
                }
            }
    }

}