package com.phooper.yammynyammy.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.ui.adapters.ProductListAdapter
import com.phooper.yammynyammy.utils.Constants.Companion.ARG_OBJECT
import com.phooper.yammynyammy.viewmodels.ProductListViewModel
import kotlinx.android.synthetic.main.fragment_product_list.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductListFragment : BaseFragment() {
    override val layoutRes = R.layout.fragment_product_list

    private val viewModel: ProductListViewModel by viewModel {
        parametersOf(arguments?.getInt(ARG_OBJECT))
    }

    private val productListAdapter: ProductListAdapter by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        recycler_view.apply {
            adapter = productListAdapter
            layoutManager = LinearLayoutManager(this@ProductListFragment.context)
        }

        viewModel.products.observe(viewLifecycleOwner, Observer { productList ->
            productListAdapter.setData(productList)
        })

        viewModel.event.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { event ->
                when (event) {
                    ProductListViewModel.ViewEvent.ERROR -> {
                        showMessage(R.string.net_error, recycler_view)
                    }
                }
            }
        })

        viewModel.state.observe(viewLifecycleOwner, Observer {
            when (it) {
                ProductListViewModel.ViewState.DEFAULT -> {
                    progress_bar.visibility = View.GONE
                }
                ProductListViewModel.ViewState.LOADING -> {
                    progress_bar.visibility = View.VISIBLE
                }
            }
        })
    }
}
