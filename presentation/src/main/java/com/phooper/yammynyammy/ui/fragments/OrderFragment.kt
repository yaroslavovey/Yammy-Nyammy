package com.phooper.yammynyammy.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.livermor.delegateadapter.delegate.diff.DiffUtilCompositeAdapter
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.ui.adapters.OrderAddressAndStateDelegateAdapter
import com.phooper.yammynyammy.ui.adapters.OrderProductDelegateAdapter
import com.phooper.yammynyammy.ui.adapters.TotalPriceDelegateAdapter
import com.phooper.yammynyammy.utils.setAppBarTitle
import com.phooper.yammynyammy.viewmodels.OrderViewModel
import kotlinx.android.synthetic.main.fragment_order.*
import kotlinx.android.synthetic.main.no_network_layout.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

@FlowPreview
@ExperimentalCoroutinesApi
class OrderFragment : BaseFragment() {

    override val layoutRes = R.layout.fragment_order

    private val diffAdapter =
        DiffUtilCompositeAdapter.Builder()
            .add(OrderAddressAndStateDelegateAdapter())
            .add(OrderProductDelegateAdapter())
            .add(TotalPriceDelegateAdapter())
            .build()

    private val viewModel by viewModel<OrderViewModel> {
        parametersOf(OrderFragmentArgs.fromBundle(requireArguments()).addressUid)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = diffAdapter
        }

        viewModel.appBarTitleDate.observe(viewLifecycleOwner, Observer { date ->
            setAppBarTitle(getString(R.string.order_date) + date)
        })

        viewModel.orderInfo.observe(viewLifecycleOwner, Observer {
            diffAdapter.swapData(it)
        })

        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            state?.let {
                when (it) {
                    OrderViewModel.ViewState.LOADING -> {
                        progress_bar.visibility = View.VISIBLE
                        recycler_view.visibility = View.VISIBLE
                        no_network_layout.visibility = View.GONE
                    }
                    OrderViewModel.ViewState.DEFAULT -> {
                        progress_bar.visibility = View.GONE
                        recycler_view.visibility = View.VISIBLE
                        no_network_layout.visibility = View.GONE
                    }
                    OrderViewModel.ViewState.NETWORK_ERROR -> {
                        progress_bar.visibility = View.GONE
                        recycler_view.visibility = View.GONE
                        no_network_layout.visibility = View.VISIBLE
                    }
                }
            }
        })

        refresh_btn.setOnClickListener { viewModel.loadOrder() }

    }
}