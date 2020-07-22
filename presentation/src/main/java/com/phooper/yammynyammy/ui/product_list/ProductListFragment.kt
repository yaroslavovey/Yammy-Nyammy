package com.phooper.yammynyammy.ui.product_list

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.entities.Product
import com.phooper.yammynyammy.presenters.product_list.ProductListPresenter
import com.phooper.yammynyammy.presenters.product_list.ProductListView
import com.phooper.yammynyammy.ui.global.BaseFragment
import com.phooper.yammynyammy.ui.global.adapters.ProductAdapter
import com.phooper.yammynyammy.utils.Constants
import com.phooper.yammynyammy.utils.Constants.Companion.ARG_OBJECT
import kotlinx.android.synthetic.main.fragment_product_list.*
import kotlinx.android.synthetic.main.no_network_layout.*
import moxy.ktx.moxyPresenter
import javax.inject.Inject


class ProductListFragment : BaseFragment(), ProductListView {

    override val layoutRes = R.layout.fragment_product_list

    @Inject
    lateinit var presenterFactory: ProductListPresenter.Factory

    private val presenter by moxyPresenter {
        presenterFactory.create(requireArguments().getInt(ARG_OBJECT))
    }

    @Inject
    lateinit var productAdapter: ProductAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        recycler_view.apply {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(this@ProductListFragment.context)
        }

        productAdapter.apply {
            onItemClick = {}
            onAddToCartBtnClick = { showAddToCartBottomSheet(it) }
        }

        refresh_btn.setOnClickListener { presenter.loadProducts() }

    }

    private fun showAddToCartBottomSheet(productId: Int) {
        AddToCartDialogFragment.create(productId)
            .show(requireActivity().supportFragmentManager, Constants.DIALOG_TAG)
    }

    override fun setProductList(list: List<Product>) {
        productAdapter.setData(list)
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

    companion object {
        fun create(category: Int) =
            ProductListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_OBJECT, category)
                }
            }
    }
}
