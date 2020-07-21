package com.phooper.yammynyammy.ui.my_addresses

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.livermor.delegateadapter.delegate.diff.DiffUtilCompositeAdapter
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.ui.global.BaseFragment
import com.phooper.yammynyammy.ui.global.adapters.AddAddressButtonDelegateAdapter
import com.phooper.yammynyammy.ui.global.adapters.AddressDelegateAdapter
import kotlinx.android.synthetic.main.fragment_my_addresses.*

class MyAddressesFragment : BaseFragment() {

    private val delegateAdapter = DiffUtilCompositeAdapter.Builder()
        .add(AddAddressButtonDelegateAdapter {
//            navController.navigate(
//                MyAddressesFragmentDirections.actionMyAddressesFragmentToAddUpdateAddress(
//                    null
//                )
//            )
        })
        .add(
            AddressDelegateAdapter(
                onItemClickListener =
                { addressUid ->
                    onItemClicked(addressUid)
                },
                onEditBtnClickListener = { addressUid ->
//                    navController.navigate(
//                        MyAddressesFragmentDirections
//                            .actionMyAddressesFragmentToAddUpdateAddress(addressUid)
//                    )
                })
        )
        .build()


    override val layoutRes = R.layout.fragment_my_addresses

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
//
//        viewModel.mode.observe(viewLifecycleOwner, Observer {
//            it?.let { viewMode ->
//                when (viewMode) {
//                    MyAddressesViewModel.ViewMode.CHECKING_OUT_ADDRESSES -> {
//                        setAppBarTitle(getString(R.string.my_addresses))
//                    }
//                    MyAddressesViewModel.ViewMode.CHOOSING_DELIVERY_ADDRESS -> {
//                        setAppBarTitle(getString(R.string.delivery_address))
//                    }
//                }
//            }
//        })

        recycler_view.apply {
            adapter = delegateAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
//
//        add_new_address_btn.setOnClickListener {
//            navController.navigate(
//                MyAddressesFragmentDirections.actionMyAddressesFragmentToAddUpdateAddress(
//                    null
//                )
//            )
//        }
//
//        refresh_btn.setOnClickListener { viewModel.loadAddresses() }
//
//        viewModel.addressesList.observe(viewLifecycleOwner, Observer {
//            delegateAdapter.swapData(it)
//        })
//
//        viewModel.state.observe(viewLifecycleOwner, Observer { viewState ->
//            viewState?.let {
//                when (it) {
//                    MyAddressesViewModel.ViewState.DEFAULT -> {
//                        progress_bar.visibility = View.GONE
//                        no_addresses_layout.visibility = View.GONE
//                        recycler_view.visibility = View.VISIBLE
//                        no_network_layout.visibility = View.GONE
//                    }
//                    MyAddressesViewModel.ViewState.LOADING -> {
//                        progress_bar.visibility = View.VISIBLE
//                        no_addresses_layout.visibility = View.GONE
//                        recycler_view.visibility = View.VISIBLE
//                        no_network_layout.visibility = View.GONE
//                    }
//                    MyAddressesViewModel.ViewState.NO_ADDRESSES -> {
//                        progress_bar.visibility = View.GONE
//                        no_addresses_layout.visibility = View.VISIBLE
//                        recycler_view.visibility = View.GONE
//                        no_network_layout.visibility = View.GONE
//                    }
//                    MyAddressesViewModel.ViewState.NETWORK_ERROR -> {
//                        progress_bar.visibility = View.GONE
//                        no_addresses_layout.visibility = View.GONE
//                        recycler_view.visibility = View.GONE
//                        no_network_layout.visibility = View.VISIBLE
//                    }
//                }
//            }
//        })
    }

    //TODO Come up with something better
    private fun onItemClicked(addressUid: String) {
//        viewModel.mode.value?.let {
//            if (it == MyAddressesViewModel.ViewMode.CHOOSING_DELIVERY_ADDRESS) {
//                sharedViewModel.selectAddress(addressUid).also { navController.popBackStack() }
//            } else {
//                navController.navigate(
//                    MyAddressesFragmentDirections.actionMyAddressesFragmentToAddUpdateAddress(
//                        addressUid
//                    )
//                )
//            }
//        }
    }
}