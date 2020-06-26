package com.phooper.yammynyammy.ui.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.livermor.delegateadapter.delegate.diff.DiffUtilCompositeAdapter
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.ui.adapters.ProductInCartDelegateAdapter
import com.phooper.yammynyammy.viewmodels.CartViewModel
import kotlinx.android.synthetic.main.fragment_cart.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : BaseFragment() {

    private val viewModel by viewModel<CartViewModel>()

    override val layoutRes = R.layout.fragment_cart

    private val delegateAdapter =
        DiffUtilCompositeAdapter.Builder().add(ProductInCartDelegateAdapter()).build()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = delegateAdapter
        }
        viewModel.productsInCart.observe(viewLifecycleOwner, Observer {
            delegateAdapter.swapData(it)
        })
    }
}