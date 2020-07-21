package com.phooper.yammynyammy.ui.product_list

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.presenters.product_list.ProductListPresenter
import com.phooper.yammynyammy.presenters.product_list.ProductListView
import com.phooper.yammynyammy.ui.global.BaseFragment
import com.phooper.yammynyammy.ui.global.adapters.ProductAdapter
import com.phooper.yammynyammy.utils.Constants
import com.phooper.yammynyammy.utils.Constants.Companion.ARG_OBJECT
import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.fragment_product_list.*
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Qualifier


class ProductListFragment : BaseFragment(), ProductListView {

    override val layoutRes = R.layout.fragment_product_list

    @Inject
    lateinit var presenterProvider: Provider<ProductListPresenter>

    private val presenter by moxyPresenter { presenterProvider.get() }

    private val productAdapter = ProductAdapter().apply {
        onItemClick = {
//            navController.navigate(
//                MenuFragmentDirections.actionMenuFragmentToProductFragment(
//                    it
//                )
//            )
        }
        onAddToCartBtnClick = {
            showAddToCartBottomSheet(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        recycler_view.apply {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(this@ProductListFragment.context)
        }

    }

    private fun showAddToCartBottomSheet(productId: Int) {
        AddToCartDialogFragment.create(productId)
            .show(requireActivity().supportFragmentManager, Constants.DIALOG_TAG)
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

    companion object {
        fun create(category: Int) =
            ProductListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_OBJECT, category)
                }
            }
    }

}
