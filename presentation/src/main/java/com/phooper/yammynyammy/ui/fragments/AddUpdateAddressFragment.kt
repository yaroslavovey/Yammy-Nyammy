package com.phooper.yammynyammy.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.utils.hideKeyboard
import com.phooper.yammynyammy.utils.setAppBarTitle
import com.phooper.yammynyammy.utils.setHideLayoutErrorOnTextChangedListener
import com.phooper.yammynyammy.utils.showMessage
import com.phooper.yammynyammy.viewmodels.AddUpdateAddressViewModel
import kotlinx.android.synthetic.main.fragment_add_update_address.*
import kotlinx.android.synthetic.main.no_network_layout.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

@FlowPreview
@ExperimentalCoroutinesApi
class AddUpdateAddressFragment : BaseFragment() {

    private lateinit var navController: NavController

    override val layoutRes = R.layout.fragment_add_update_address

    private val viewModel by inject<AddUpdateAddressViewModel> {
        parametersOf(
            AddUpdateAddressFragmentArgs.fromBundle(
                requireArguments()
            ).addressUid
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navController = findNavController()
        initViews()
    }

    private fun initViews() {
        street_input.setHideLayoutErrorOnTextChangedListener(street_input_layout)
        house_num_input.setHideLayoutErrorOnTextChangedListener(house_num_input_layout)
        apartment_num_input.setHideLayoutErrorOnTextChangedListener(apartment_num_input_layout)

        viewModel.mode.observe(viewLifecycleOwner, Observer {
            it?.let { mode ->
                when (mode) {
                    AddUpdateAddressViewModel.ViewMode.UPDATE_ADDRESS -> {
                        add_update_btn.apply {
                            text = getString(R.string.update_address)
                            setOnClickListener {
                                if (areSomeInputsEmpty()) {
                                    showFillFieldsError()
                                } else {
                                    hideKeyboard()
                                    viewModel.updateAddress(
                                        street_input.text.toString(),
                                        house_num_input.text.toString(),
                                        apartment_num_input.text.toString()
                                    )
                                }
                            }
                        }
                        setAppBarTitle(getString(R.string.edit_address))
                        delete_address_btn.visibility = View.VISIBLE
                    }
                    AddUpdateAddressViewModel.ViewMode.NEW_ADDRESS -> {
                        add_update_btn.apply {
                            text = getString(R.string.add_address)
                            setOnClickListener {
                                if (areSomeInputsEmpty()) {
                                    showFillFieldsError()
                                } else {
                                    hideKeyboard()
                                    viewModel.addAddress(
                                        street_input.text.toString(),
                                        house_num_input.text.toString(),
                                        apartment_num_input.text.toString()
                                    )
                                }
                            }
                        }
                        setAppBarTitle(getString(R.string.new_address))
                        delete_address_btn.visibility = View.GONE
                    }
                }
            }
        })

        viewModel.state.observe(viewLifecycleOwner, Observer {
            it?.let { state ->
                when (state) {
                    AddUpdateAddressViewModel.ViewState.DEFAULT -> {
                        add_update_btn.visibility = View.VISIBLE
                        progress_bar.visibility = View.GONE
                        no_network_layout.visibility = View.GONE
                        inputs_scroll_view.visibility = View.VISIBLE
                    }
                    AddUpdateAddressViewModel.ViewState.LOADING -> {
                        add_update_btn.visibility = View.VISIBLE
                        progress_bar.visibility = View.VISIBLE
                        no_network_layout.visibility = View.GONE
                        inputs_scroll_view.visibility = View.VISIBLE
                    }
                    AddUpdateAddressViewModel.ViewState.NETWORK_ERROR -> {
                        add_update_btn.visibility = View.GONE
                        no_network_layout.visibility = View.VISIBLE
                        progress_bar.visibility = View.GONE
                        inputs_scroll_view.visibility = View.GONE
                    }
                }
            }
        })

        viewModel.address.observe(viewLifecycleOwner, Observer { address ->
            street_input.setText(address.street)
            house_num_input.setText(address.houseNum)
            apartment_num_input.setText(address.apartNum)
        })

        refresh_btn.setOnClickListener { viewModel.loadAddress() }

        delete_address_btn.setOnClickListener { showDeleteDialog() }

        viewModel.event.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { event ->
                when (event) {
                    AddUpdateAddressViewModel.ViewEvent.ERROR -> {
                        showMessage(R.string.error)
                    }
                    AddUpdateAddressViewModel.ViewEvent.DELETE_SUCCESS -> {
                        showMessage(R.string.address_deleted)
                    }
                    AddUpdateAddressViewModel.ViewEvent.CREATE_SUCCESS -> {
                        showMessage(R.string.address_added)
                    }
                    AddUpdateAddressViewModel.ViewEvent.UPDATE_SUCCESS -> {
                        showMessage(R.string.address_updated)
                    }
                }
                navController.popBackStack()
            }
        })
    }

    private fun showDeleteDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.are_you_sure)
            .setMessage(R.string.address_will_be_deleted)
            .setNegativeButton(R.string.no) { _, _ -> }
            .setPositiveButton(R.string.yes) { _, _ ->
                hideKeyboard()
                viewModel.deleteAddress()
            }
            .show()
    }

    private fun areSomeInputsEmpty() =
        street_input.text.toString().isEmpty() ||
                apartment_num_input.text.toString().isEmpty() ||
                house_num_input.text.toString().isEmpty()

    private fun showFillFieldsError() {
        if (street_input.text.toString().isEmpty())
            street_input_layout.error =
                getString(R.string.fill_street)

        if (house_num_input.text.toString().isEmpty())
            house_num_input_layout.error = getString(R.string.fill_house_num)

        if (apartment_num_input.text.toString().isEmpty())
            apartment_num_input_layout.error = getString(R.string.fill_apart_num)
    }
}