package com.phooper.yammynyammy.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.livermor.delegateadapter.delegate.diff.DiffUtilCompositeAdapter
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.ui.adapters.AddAddressButtonDelegateAdapter
import com.phooper.yammynyammy.ui.adapters.AddressDelegateAdapter
import com.phooper.yammynyammy.utils.setAppBarTitle
import com.phooper.yammynyammy.viewmodels.MainContainerViewModel
import com.phooper.yammynyammy.viewmodels.MyAddressesViewModel
import kotlinx.android.synthetic.main.fragment_my_addresses.*
import kotlinx.android.synthetic.main.item_add_new_address_btn.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MyAddressesFragment : BaseFragment() {

    private val delegateAdapter = DiffUtilCompositeAdapter.Builder()
        .add(AddAddressButtonDelegateAdapter {
            navController.navigate(MyAddressesFragmentDirections.actionMyAddressesFragmentToAddUpdateAddress())
        })
        .add(
            AddressDelegateAdapter(
                onItemClickListener =
                { addressUid ->
                    onItemClicked(addressUid)
                },
                onEditBtnClickListener = { addressUid ->
                    navController.navigate(
                        MyAddressesFragmentDirections
                            .actionMyAddressesFragmentToAddUpdateAddress(addressUid)
                    )
                })
        )
        .build()

    private lateinit var navController: NavController

    private val viewModel by viewModel<MyAddressesViewModel> {
        parametersOf(
            MyAddressesFragmentArgs.fromBundle(
                requireArguments()
            ).choosingAddressForDelivery
        )
    }

    private val sharedViewModel by sharedViewModel<MainContainerViewModel>()

    override val layoutRes = R.layout.fragment_my_addresses

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navController = findNavController()
        initViews()
    }

    private fun initViews() {

        viewModel.mode.observe(viewLifecycleOwner, Observer {
            it?.let { viewMode ->
                when (viewMode) {
                    MyAddressesViewModel.ViewMode.CHECKING_OUT_ADDRESSES -> {
                        setAppBarTitle(getString(R.string.my_addresses))
                    }
                    MyAddressesViewModel.ViewMode.CHOOSING_DELIVERY_ADDRESS -> {
                        setAppBarTitle(getString(R.string.delivery_address))
                    }
                }
            }
        })

        recycler_view.apply {
            adapter = delegateAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        add_new_address_btn.setOnClickListener {
            navController.navigate(MyAddressesFragmentDirections.actionMyAddressesFragmentToAddUpdateAddress())
        }
        //TODO fix addresses list nullability
        viewModel.addressesList?.observe(viewLifecycleOwner, Observer {
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

    //TODO Come up with something better
    private fun onItemClicked(addressUid: String) {
        viewModel.mode.value?.let {
            if (it == MyAddressesViewModel.ViewMode.CHOOSING_DELIVERY_ADDRESS) {
                sharedViewModel.selectAddress(addressUid).also { navController.popBackStack() }
            } else {
                navController.navigate(
                    MyAddressesFragmentDirections.actionMyAddressesFragmentToAddUpdateAddress(
                        addressUid
                    )
                )
            }
        }
    }
}