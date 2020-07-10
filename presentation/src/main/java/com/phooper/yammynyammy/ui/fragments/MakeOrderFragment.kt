package com.phooper.yammynyammy.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.utils.hideKeyboard
import com.phooper.yammynyammy.utils.setHideLayoutErrorOnTextChangedListener
import com.phooper.yammynyammy.utils.showMessage
import com.phooper.yammynyammy.utils.showMessageAboveBottomNav
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
        name_input.setHideLayoutErrorOnTextChangedListener(name_input_layout)
        phone_number_input.setHideLayoutErrorOnTextChangedListener(phone_number_input_layout)
        address_input.setHideLayoutErrorOnTextChangedListener(address_input_layout)

        accept_order_btn.setOnClickListener {
            if (areSomeInputsEmpty()) {
                showFillFieldsError()
            } else {
                viewModel.makeOrder(
                    name_input.text.toString(),
                    phone_number_input.text.toString(),
                    address_input.text.toString()
                )
            }
        }

        address_input.setOnClickListener {
            sharedViewModel.resetAddress()

            navController.navigate(
                MakeOrderFragmentDirections.actionMakeOrderFragmentToMyAddressesFragment(
                    choosingAddressForDelivery = true
                )
            )
        }

        sharedViewModel.selectedAddress.observe(viewLifecycleOwner, Observer {
            address_input.setText(it)
        })

        viewModel.userData.observe(viewLifecycleOwner, Observer {
            phone_number_input.setText(it.phoneNum)
            name_input.setText(it.name)
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

        viewModel.event.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { event ->
                when (event) {
                    MakeOrderViewModel.ViewEvent.SUCCESS -> {
                        //TODO Come up with something better
                        requireActivity().showMessageAboveBottomNav(R.string.order_was_made_successfully)
                        //
                        hideKeyboard()
                        navController.navigate(R.id.action_make_order_fragment_to_orders_fragment)
                    }
                    MakeOrderViewModel.ViewEvent.FAILURE -> {
                        requireActivity().showMessage(R.string.error)
                    }
                }
            }
        })
    }

    private fun areSomeInputsEmpty() =
        name_input.text.toString().isEmpty() ||
                phone_number_input.text.toString().isEmpty() ||
                address_input.text.toString().isEmpty()

    private fun showFillFieldsError() {
        if (name_input.text.toString().isEmpty())
            name_input_layout.error =
                getString(R.string.fill_name)

        if (phone_number_input.text.toString().isEmpty())
            phone_number_input_layout.error = getString(R.string.fill_phone)

        if (address_input.text.toString().isEmpty())
            address_input_layout.error = getString(R.string.fill_address)

    }
}