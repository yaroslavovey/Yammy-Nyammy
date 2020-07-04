package com.phooper.yammynyammy.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.ui.activities.MainContainerActivity
import com.phooper.yammynyammy.viewmodels.ProductViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.dialog_fragment_add_to_cart.add_to_cart_btn
import kotlinx.android.synthetic.main.dialog_fragment_add_to_cart.count_text
import kotlinx.android.synthetic.main.dialog_fragment_add_to_cart.minus_btn
import kotlinx.android.synthetic.main.dialog_fragment_add_to_cart.plus_btn
import kotlinx.android.synthetic.main.fragment_product.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductFragment : BaseFragment() {
    override val layoutRes = R.layout.fragment_product
    private val viewModel by viewModel<ProductViewModel> {
        parametersOf(
            ProductFragmentArgs.fromBundle(
                requireArguments()
            ).productId
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
        viewModel.itemCount.observe(viewLifecycleOwner, Observer {
            count_text.text = it.toString()
        })

        viewModel.description.observe(viewLifecycleOwner, Observer {
            product_description.text = it
        })

        viewModel.totalPrice.observe(viewLifecycleOwner, Observer {
            product_price.text = it
        })

        viewModel.productTitle.observe(viewLifecycleOwner, Observer {
            (requireActivity() as AppCompatActivity).supportActionBar?.title = it
        })

        viewModel.imgLink.observe(viewLifecycleOwner, Observer {
            picasso.load(it).into(product_image)
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
                }
            }
        })

        add_to_cart_btn.setOnClickListener {
            viewModel.addProductsToCart()
            //TODO Come up with something better
            (requireActivity() as MainContainerActivity).showAddedToCartSnackBar()
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