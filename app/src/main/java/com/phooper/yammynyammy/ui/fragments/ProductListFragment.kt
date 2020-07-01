package com.phooper.yammynyammy.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.ui.adapters.ProductAdapter
import com.phooper.yammynyammy.utils.Constants
import com.phooper.yammynyammy.utils.Constants.Companion.ARG_OBJECT
import com.phooper.yammynyammy.utils.showMessage
import com.phooper.yammynyammy.viewmodels.ProductListViewModel
import kotlinx.android.synthetic.main.fragment_product_list.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductListFragment : BaseFragment() {
    override val layoutRes = R.layout.fragment_product_list
    private lateinit var navController: NavController

    private val viewModel: ProductListViewModel by viewModel {
        parametersOf(arguments?.getInt(ARG_OBJECT))
    }

    private val productAdapter: ProductAdapter by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController(requireActivity(), R.id.main_nav_host_fragment)
        initViews()
    }

    private fun initViews() {
        recycler_view.apply {
            adapter = productAdapter.apply {
                onItemClick = {
                    navController.navigate(
                        MenuFragmentDirections.actionMenuFragmentToProductFragment(
                            it
                        )
                    )
                }
                onAddToCartBtnClick = {
                    showAddToCartBottomSheet(it)
                }
            }
            layoutManager = LinearLayoutManager(this@ProductListFragment.context)
        }
        viewModel.products.observe(viewLifecycleOwner, Observer { productList ->
            productAdapter.setData(productList)
        })

        viewModel.event.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { event ->
                when (event) {
                    ProductListViewModel.ViewEvent.ERROR -> {
                        requireActivity().showMessage(R.string.net_error)
                    }
                }
            }
        })

        viewModel.state.observe(viewLifecycleOwner, Observer {
            it?.let { state ->
                when (state) {
                    ProductListViewModel.ViewState.DEFAULT -> {
                        progress_bar.visibility = View.GONE
                    }
                    ProductListViewModel.ViewState.LOADING -> {
                        progress_bar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun showAddToCartBottomSheet(productId: Int) {
        AddToCartDialogFragment.create(productId)
            .show(requireActivity().supportFragmentManager, Constants.DIALOG_TAG)
    }

}
