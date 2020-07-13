package com.phooper.yammynyammy.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.utils.setAppBarTitle
import com.phooper.yammynyammy.viewmodels.MainContainerViewModel
import com.phooper.yammynyammy.viewmodels.ProductViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.dialog_fragment_add_to_cart.add_to_cart_btn
import kotlinx.android.synthetic.main.dialog_fragment_add_to_cart.count_text
import kotlinx.android.synthetic.main.dialog_fragment_add_to_cart.minus_btn
import kotlinx.android.synthetic.main.dialog_fragment_add_to_cart.plus_btn
import kotlinx.android.synthetic.main.fragment_product.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

@FlowPreview
@ExperimentalCoroutinesApi
class ProductFragment : BaseFragment() {

    override val layoutRes = R.layout.fragment_product

    private val sharedViewModel by sharedViewModel<MainContainerViewModel>()

    private val viewModel by viewModel<ProductViewModel> {
        parametersOf(
            ProductFragmentArgs.fromBundle(requireArguments()).productId
        )
    }

    private val picasso by inject<Picasso>()

    private lateinit var navController: NavController

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navController = findNavController(requireActivity(), R.id.main_nav_host_fragment)
        initViews()
    }

    private fun initViews() {

        viewModel.totalPrice.observe(viewLifecycleOwner, Observer {
            product_price.text = it
        })

        viewModel.itemCount.observe(viewLifecycleOwner, Observer {
            count_text.text = it
        })

        viewModel.product.observe(viewLifecycleOwner, Observer { product ->
            setAppBarTitle(product.title)
            picasso.load(product.imageURL).into(product_image)
            product_description.text = product.desc
        })

        viewModel.state.observe(viewLifecycleOwner, Observer { viewState ->
            viewState?.let {
                when (it) {
                    ProductViewModel.ViewState.LOADING -> {
                        progress_bar.visibility = View.VISIBLE
                    }
                    ProductViewModel.ViewState.DEFAULT -> {
                        progress_bar.visibility = View.GONE
                    }
                    ProductViewModel.ViewState.NETWORK_ERROR -> {
                        //TODO
                    }
                }
            }
        })

        add_to_cart_btn.setOnClickListener {
            viewModel.addProductsToCart()
            sharedViewModel.triggerAddedToCartEvent()
            navController.popBackStack()
        }

        plus_btn.setOnClickListener {
            viewModel.increaseItemCountByOne()
        }

        minus_btn.setOnClickListener {
            viewModel.decreaseItemCountByOne()
        }

    }
}