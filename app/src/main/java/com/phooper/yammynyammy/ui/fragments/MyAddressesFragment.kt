package com.phooper.yammynyammy.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.livermor.delegateadapter.delegate.diff.DiffUtilCompositeAdapter
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.ui.adapters.AddAddressButtonDelegateAdapter
import com.phooper.yammynyammy.ui.adapters.AddressDelegateAdapter
import com.phooper.yammynyammy.viewmodels.MainContainerViewModel
import com.phooper.yammynyammy.viewmodels.MyAddressesViewModel
import kotlinx.android.synthetic.main.fragment_my_addresses.*
import kotlinx.android.synthetic.main.item_add_new_address_btn.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyAddressesFragment : BaseFragment() {

    private val delegateAdapter = DiffUtilCompositeAdapter.Builder()
        .add(AddAddressButtonDelegateAdapter {
            navController.navigate(MyAddressesFragmentDirections.actionMyAddressesFragmentToAddUpdateAddress())
        })
        .add(AddressDelegateAdapter({
            sharedViewModel.selectAddress(it).also { navController.popBackStack() }
        }, { address_uid ->
            navController.navigate(
                MyAddressesFragmentDirections.actionMyAddressesFragmentToAddUpdateAddress(
                    address_uid
                )
            )
        }))
        .build()

    private lateinit var navController: NavController

    private val viewModel by viewModel<MyAddressesViewModel>()

    private val sharedViewModel by sharedViewModel<MainContainerViewModel>()

    override val layoutRes = R.layout.fragment_my_addresses

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navController = findNavController()
        initViews()
    }

    private fun initViews() {

        add_new_address_btn.setOnClickListener {
            navController.navigate(MyAddressesFragmentDirections.actionMyAddressesFragmentToAddUpdateAddress())
        }

        recycler_view.apply {
            adapter = delegateAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.addressesList.observe(viewLifecycleOwner, Observer {
            delegateAdapter.swapData(it)
        })

        viewModel.state.observe(viewLifecycleOwner, Observer { viewState ->
            viewState?.let {
                when (it) {
                    MyAddressesViewModel.ViewState.DEFAULT -> {
                        progress_bar.visibility = View.GONE
                        no_addresses_layout.visibility = View.GONE
                        recycler_view.visibility = View.VISIBLE
                    }
                    MyAddressesViewModel.ViewState.LOADING -> {
                        progress_bar.visibility = View.VISIBLE
                        no_addresses_layout.visibility = View.GONE
                        recycler_view.visibility = View.VISIBLE
                    }
                    MyAddressesViewModel.ViewState.NO_ADDRESSES -> {
                        progress_bar.visibility = View.GONE
                        recycler_view.visibility = View.GONE
                        no_addresses_layout.visibility = View.VISIBLE
                    }
                }
            }
        })
    }
}