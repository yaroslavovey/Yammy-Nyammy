package com.phooper.yammynyammy.ui.edit_profile

import android.os.Bundle
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.ui.global.BaseFragment
import com.phooper.yammynyammy.utils.setHideLayoutErrorOnTextChangedListener
import kotlinx.android.synthetic.main.fragment_edit_profile.*

class EditProfileFragment : BaseFragment() {

    override val layoutRes = R.layout.fragment_edit_profile


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        name_input.setHideLayoutErrorOnTextChangedListener(name_input_layout)
        phone_number_input.setHideLayoutErrorOnTextChangedListener(phone_number_input_layout)
//
//        save_btn.setOnClickListener {
//            viewModel.saveUser(name_input.text.toString(), phone_number_input.text.toString())
//        }
//
//        viewModel.userData.observe(viewLifecycleOwner, Observer {
//            phone_number_input.setText(it.phoneNum)
//            name_input.setText(it.name)
//        })
//
//        viewModel.state.observe(viewLifecycleOwner, Observer {
//            it?.let { state ->
//                when (state) {
//                    EditProfileViewModel.ViewState.LOADING -> {
//                        progress_bar.visibility = View.VISIBLE
//                        no_network_layout.visibility = View.GONE
//                        edit_profile_layout.visibility = View.VISIBLE
//                    }
//                    EditProfileViewModel.ViewState.DEFAULT -> {
//                        progress_bar.visibility = View.GONE
//                        edit_profile_layout.visibility = View.VISIBLE
//                        no_network_layout.visibility = View.GONE
//                    }
//                    EditProfileViewModel.ViewState.NO_NETWORK -> {
//                        edit_profile_layout.visibility = View.GONE
//                        no_network_layout.visibility = View.VISIBLE
//                    }
//                }
//            }
//        })
//
//        viewModel.event.observe(viewLifecycleOwner, Observer {
//            it.getContentIfNotHandled()?.let { event ->
//                when (event) {
//                    EditProfileViewModel.ViewEvent.FAILURE -> {
//                        showMessage(R.string.error)
//                    }
//                    EditProfileViewModel.ViewEvent.SUCCESS -> {
//                        showMessageAboveBottomNav(R.string.your_data_has_been_updated)
//                        navController.popBackStack()
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
//        })
//
//        refresh_btn.setOnClickListener { viewModel.loadUser() }

    }
}