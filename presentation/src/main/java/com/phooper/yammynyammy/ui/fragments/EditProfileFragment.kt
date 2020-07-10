package com.phooper.yammynyammy.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.utils.setHideLayoutErrorOnTextChangedListener
import com.phooper.yammynyammy.utils.showMessage
import com.phooper.yammynyammy.viewmodels.EditProfileViewModel
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditProfileFragment : BaseFragment() {

    private val viewModel by viewModel<EditProfileViewModel>()

    override val layoutRes = R.layout.fragment_edit_profile

    private lateinit var navController: NavController

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navController = findNavController()
        initViews()
    }

    private fun initViews() {
        name_input.setHideLayoutErrorOnTextChangedListener(name_input_layout)
        phone_number_input.setHideLayoutErrorOnTextChangedListener(phone_number_input_layout)

        save_btn.setOnClickListener {
            if (areSomeInputsEmpty()) {
                showFillFieldsError()
            } else {
                viewModel.saveUser(name_input.text.toString(), phone_number_input.text.toString())
            }
        }

        viewModel.userData.observe(viewLifecycleOwner, Observer {
            phone_number_input.setText(it.phoneNum)
            name_input.setText(it.name)
        })

        viewModel.state.observe(viewLifecycleOwner, Observer {
            it?.let { state ->
                when (state) {
                    EditProfileViewModel.ViewState.LOADING -> {
                        progress_bar.visibility = View.VISIBLE
                    }
                    EditProfileViewModel.ViewState.DEFAULT -> {
                        progress_bar.visibility = View.GONE
                    }
                }
            }
        })

        viewModel.event.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { event ->
                when (event) {
                    EditProfileViewModel.ViewEvent.FAILURE -> {
                        requireActivity().showMessage(R.string.error)
                    }
                    EditProfileViewModel.ViewEvent.SUCCESS -> {
                        requireActivity().showMessage(R.string.your_data_has_been_updated)
                        navController.popBackStack()
                    }
                }
            }
        })
    }

    private fun areSomeInputsEmpty() =
        name_input.text.toString().isEmpty() ||
                phone_number_input.text.toString().isEmpty()

    private fun showFillFieldsError() {
        if (name_input.text.toString().isEmpty())
            name_input_layout.error =
                getString(R.string.fill_name)

        if (phone_number_input.text.toString().isEmpty())
            phone_number_input_layout.error = getString(R.string.fill_phone)

    }
}