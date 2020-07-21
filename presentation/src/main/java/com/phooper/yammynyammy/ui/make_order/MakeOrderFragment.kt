package com.phooper.yammynyammy.ui.make_order

import android.os.Bundle
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.ui.global.BaseFragment
import com.phooper.yammynyammy.utils.setHideLayoutErrorOnTextChangedListener
import kotlinx.android.synthetic.main.fragment_make_order.*

class MakeOrderFragment : BaseFragment() {


    override val layoutRes = R.layout.fragment_make_order

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        name_input.setHideLayoutErrorOnTextChangedListener(name_input_layout)
        phone_number_input.setHideLayoutErrorOnTextChangedListener(phone_number_input_layout)
        address_input.setHideLayoutErrorOnTextChangedListener(address_input_layout)
//
//        accept_order_btn.setOnClickListener {
//            viewModel.saveUserAndMakeOrder(
//                name_input.text.toString(),
//                phone_number_input.text.toString(),
//                address_input.text.toString()
//            )
//        }
//
//        refresh_btn.setOnClickListener {
//            viewModel.loadUser()
//        }
//
//        address_input.setOnClickListener {
//            sharedViewModel.resetAddress()
//
//            navController.navigate(
//                MakeOrderFragmentDirections.actionMakeOrderFragmentToMyAddressesFragment(
//                    choosingAddressForDelivery = true
//                )
//            )
//        }
//
//        sharedViewModel.selectedAddress.observe(viewLifecycleOwner, Observer {
//            address_input.setText(it)
//        })
//
//        viewModel.userData.observe(viewLifecycleOwner, Observer {
//            phone_number_input.setText(it.phoneNum)
//            name_input.setText(it.name)
//        })
//
//        viewModel.state.observe(viewLifecycleOwner, Observer { viewState ->
//            viewState?.let {
//                when (it) {
//                    MakeOrderViewModel.ViewState.LOADING -> {
//                        progress_bar.visibility = View.VISIBLE
//                        make_order_layout.visibility = View.VISIBLE
//                        no_network_layout.visibility = View.GONE
//                    }
//                    MakeOrderViewModel.ViewState.DEFAULT -> {
//                        progress_bar.visibility = View.GONE
//                        make_order_layout.visibility = View.VISIBLE
//                        no_network_layout.visibility = View.GONE
//                    }
//                    MakeOrderViewModel.ViewState.NO_NETWORK -> {
//                        make_order_layout.visibility = View.GONE
//                        no_network_layout.visibility = View.VISIBLE
//                    }
//                }
//            }
//        })
//
//        viewModel.event.observe(viewLifecycleOwner, Observer {
//            it.getContentIfNotHandled()?.let { event ->
//                when (event) {
//                    MakeOrderViewModel.ViewEvent.SUCCESS -> {
//                        showMessageAboveBottomNav(R.string.order_was_made_successfully)
//                        hideKeyboard()
//                        navController.navigate(R.id.action_make_order_fragment_to_orders_fragment)
//                    }
//                    MakeOrderViewModel.ViewEvent.FAILURE -> {
//                        showMessage(R.string.error)
//                    }
//                }
//            }
//        })
//
//        viewModel.inputValidator.observe(viewLifecycleOwner, Observer { inputValidator ->
//            name_input_layout.error =
//                inputValidator.nameInputErrorResId?.let { getString(it) }
//
//            phone_number_input_layout.error =
//                inputValidator.phoneNumInputErrorResId?.let { getString(it) }
//
//            address_input_layout.error =
//                inputValidator.addressInputErrorResId?.let { getString(it) }
//        })

    }
}