package com.phooper.yammynyammy.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.viewmodels.MainContainerViewModel
import com.phooper.yammynyammy.viewmodels.MakeOrderViewModel
import kotlinx.android.synthetic.main.fragment_make_order.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MakeOrderFragment : BaseFragment() {

    private val viewModel by viewModel<MakeOrderViewModel>()

    private val sharedViewModel by sharedViewModel<MainContainerViewModel>()

    private lateinit var navController: NavController

    override val layoutRes = R.layout.fragment_make_order

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navController = findNavController()
        initViews()
    }

    private fun initViews() {
        sharedViewModel.selectedAddress.observe(viewLifecycleOwner, Observer {
            address_input.setText(it)
        })

        address_input.setOnClickListener { navController.navigate(R.id.action_make_order_fragment_to_myAddressesFragment) }

        viewModel.phoneNum.observe(viewLifecycleOwner, Observer {
            phone_number_input.setText(it)
        })

        viewModel.username.observe(viewLifecycleOwner, Observer {
            name_input.setText(it)
        })

        viewModel.state.observe(viewLifecycleOwner, Observer { viewState ->
            viewState?.let {
                when (it) {
                    MakeOrderViewModel.ViewState.LOADING -> {
                        progress_bar.visibility = View.VISIBLE
                    }
                    MakeOrderViewModel.ViewState.DEFAULT -> {
                        progress_bar.visibility = View.GONE
                    }
                }
            }
        })
    }
}