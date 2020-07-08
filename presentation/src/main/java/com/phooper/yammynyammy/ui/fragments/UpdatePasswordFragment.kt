package com.phooper.yammynyammy.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.utils.setHideLayoutErrorOnTextChangedListener
import com.phooper.yammynyammy.utils.showMessage
import com.phooper.yammynyammy.utils.showMessageAboveBottomNav
import com.phooper.yammynyammy.viewmodels.UpdatePasswordViewModel
import kotlinx.android.synthetic.main.fragment_update_password.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpdatePasswordFragment : BaseFragment() {

    override val layoutRes = R.layout.fragment_update_password

    private val viewModel by viewModel<UpdatePasswordViewModel>()

    private lateinit var navController: NavController

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navController = findNavController()
        initViews()
    }

    private fun initViews() {
        new_password_input
            .setHideLayoutErrorOnTextChangedListener(new_password_input_layout)
        new_password_repeat_input
            .setHideLayoutErrorOnTextChangedListener(new_password_repeat_input_layout)

        update_btn.setOnClickListener {
            if (areSomeInputsEmpty()) {
                showFillFieldsError()
                return@setOnClickListener
            }

            if (!doesPasswordsMatch()) {
                showPasswordsDoesNotMatchError()
                return@setOnClickListener
            }

            if (arePasswordsTooShort()) {
                showPasswordsTooShortError()
                return@setOnClickListener
            }
            viewModel.updatePassword(
                currentPassword = current_password_input.text.toString(),
                newPassword = new_password_input.text.toString()
            )
        }

        viewModel.event.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { event ->
                when (event) {
                    UpdatePasswordViewModel.ViewEvent.SUCCESS -> {
                        requireActivity().showMessageAboveBottomNav(R.string.password_updated)
                        navController.popBackStack()
                    }
                    UpdatePasswordViewModel.ViewEvent.FAILURE -> {
                        requireActivity().showMessage(R.string.error)
                    }
                }
            }
        })

        viewModel.state.observe(viewLifecycleOwner, Observer {
            it?.let { state ->
                when (state) {
                    UpdatePasswordViewModel.ViewState.DEFAULT -> {
                        progress_bar.visibility = View.GONE
                    }
                    UpdatePasswordViewModel.ViewState.LOADING -> {
                        progress_bar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun areSomeInputsEmpty() =
        new_password_input.text.toString().isEmpty() ||
                new_password_repeat_input.text.toString().isEmpty() ||
                current_password_input.text.toString().isEmpty()

    private fun showFillFieldsError() {
        if (new_password_input.text.toString().isEmpty())
            new_password_input_layout.error =
                getString(R.string.fill_password)

        if (new_password_repeat_input.text.toString().isEmpty())
            new_password_repeat_input_layout.error = getString(R.string.fill_password)

        if (current_password_input.text.toString().isEmpty())
            current_password_input_layout.error = getString(R.string.fill_password)
    }

    private fun doesPasswordsMatch() =
        new_password_input.text.toString() ==
                new_password_repeat_input.text.toString()

    private fun showPasswordsDoesNotMatchError() {
        new_password_input_layout.error = getString(R.string.passwords_does_not_match)
        new_password_repeat_input_layout.error = getString(R.string.passwords_does_not_match)
    }

    private fun arePasswordsTooShort() =
        new_password_input.text.toString().length < 7

    private fun showPasswordsTooShortError() {
        new_password_input_layout.error = getString(R.string.passwords_are_to_short)
        new_password_repeat_input_layout.error = getString(R.string.passwords_are_to_short)
    }
}