package com.phooper.yammynyammy.ui.add_update_address

import android.os.Bundle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.ui.global.BaseFragment
import com.phooper.yammynyammy.utils.hideKeyboardKtx
import com.phooper.yammynyammy.utils.setHideLayoutErrorOnTextChangedListener
import kotlinx.android.synthetic.main.fragment_add_update_address.*

class AddUpdateAddressFragment : BaseFragment() {

    override val layoutRes = R.layout.fragment_add_update_address

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        street_input.setHideLayoutErrorOnTextChangedListener(street_input_layout)
        house_num_input.setHideLayoutErrorOnTextChangedListener(house_num_input_layout)
        apartment_num_input.setHideLayoutErrorOnTextChangedListener(apartment_num_input_layout)

//        viewModel.inputValidator.observe(viewLifecycleOwner, Observer { inputValidator ->
//            street_input_layout.error =
//                inputValidator.streetInputErrorResId?.let { getString(it) }
//
//            apartment_num_input_layout.error =
//                inputValidator.apartNumInputErrorResId?.let { getString(it) }
//
//            house_num_input_layout.error =
//                inputValidator.houseNumInputErrorResId?.let { getString(it) }
//        })
//
//        viewModel.mode.observe(viewLifecycleOwner, Observer {
//            it?.let { mode ->
//                when (mode) {
//                    AddUpdateAddressViewModel.ViewMode.UPDATE_ADDRESS -> {
//                        add_update_btn.text = getString(R.string.update_address)
//                        setAppBarTitle(getString(R.string.edit_address))
//                        delete_address_btn.visibility = View.VISIBLE
//                    }
//                    AddUpdateAddressViewModel.ViewMode.NEW_ADDRESS -> {
//                        add_update_btn.text = getString(R.string.add_address)
//                        setAppBarTitle(getString(R.string.new_address))
//                        delete_address_btn.visibility = View.GONE
//                    }
//                }
//            }
//        })
//
//        viewModel.state.observe(viewLifecycleOwner, Observer {
//            it?.let { state ->
//                when (state) {
//                    AddUpdateAddressViewModel.ViewState.DEFAULT -> {
//                        add_update_btn.visibility = View.VISIBLE
//                        progress_bar.visibility = View.GONE
//                        no_network_layout.visibility = View.GONE
//                        inputs_scroll_view.visibility = View.VISIBLE
//                    }
//                    AddUpdateAddressViewModel.ViewState.LOADING -> {
//                        add_update_btn.visibility = View.VISIBLE
//                        progress_bar.visibility = View.VISIBLE
//                        no_network_layout.visibility = View.GONE
//                        inputs_scroll_view.visibility = View.VISIBLE
//                    }
//                    AddUpdateAddressViewModel.ViewState.NETWORK_ERROR -> {
//                        add_update_btn.visibility = View.GONE
//                        no_network_layout.visibility = View.VISIBLE
//                        progress_bar.visibility = View.GONE
//                        inputs_scroll_view.visibility = View.GONE
//                    }
//                }
//            }
//        })
//
//        viewModel.address.observe(viewLifecycleOwner, Observer { address ->
//            street_input.setText(address.street)
//            house_num_input.setText(address.houseNum)
//            apartment_num_input.setText(address.apartNum)
//        })
//
//        viewModel.event.observe(viewLifecycleOwner, Observer {
//            it.getContentIfNotHandled()?.let { event ->
//                when (event) {
//                    AddUpdateAddressViewModel.ViewEvent.ERROR -> {
//                        showMessage(R.string.error)
//                    }
//                    AddUpdateAddressViewModel.ViewEvent.DELETE_SUCCESS -> {
//                        showMessage(R.string.address_deleted)
//                    }
//                    AddUpdateAddressViewModel.ViewEvent.CREATE_SUCCESS -> {
//                        showMessage(R.string.address_added)
//                    }
//                    AddUpdateAddressViewModel.ViewEvent.UPDATE_SUCCESS -> {
//                        showMessage(R.string.address_updated)
//                    }
//                }
//                navController.popBackStack()
//            }
//        })
//
//        add_update_btn.setOnClickListener {
//            hideKeyboard()
//            viewModel.addUpdateAddress(
//                street_input.text.toString(),
//                house_num_input.text.toString(),
//                apartment_num_input.text.toString()
//            )
//        }

//        refresh_btn.setOnClickListener { viewModel.loadAddress() }

        delete_address_btn.setOnClickListener { showDeleteDialog() }
    }

    private fun showDeleteDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.are_you_sure)
            .setMessage(R.string.address_will_be_deleted)
            .setNegativeButton(R.string.no) { _, _ -> }
            .setPositiveButton(R.string.yes) { _, _ ->
                hideKeyboardKtx()
//                viewModel.deleteAddress()
            }
            .show()
    }

    companion object {
        private const val ARG_ADDRESS_ID = "arg_address_id"

        fun create(addressId: String?) =
            AddUpdateAddressFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ADDRESS_ID, addressId)
                }
            }
    }

}