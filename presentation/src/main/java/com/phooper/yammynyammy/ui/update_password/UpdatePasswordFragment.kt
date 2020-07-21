package com.phooper.yammynyammy.ui.update_password

import android.os.Bundle
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.ui.global.BaseFragment
import com.phooper.yammynyammy.utils.setHideLayoutErrorOnTextChangedListener
import kotlinx.android.synthetic.main.fragment_update_password.*

class UpdatePasswordFragment : BaseFragment() {

    override val layoutRes = R.layout.fragment_update_password

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        current_password_input
            .setHideLayoutErrorOnTextChangedListener(current_password_input_layout)
        new_password_input
            .setHideLayoutErrorOnTextChangedListener(new_password_input_layout)
        new_password_input
            .setHideLayoutErrorOnTextChangedListener(new_password_repeat_input_layout)
        new_password_repeat_input
            .setHideLayoutErrorOnTextChangedListener(new_password_repeat_input_layout)
        new_password_repeat_input
            .setHideLayoutErrorOnTextChangedListener(new_password_input_layout)
//
//        update_btn.setOnClickListener {
//            viewModel.updatePassword(
//                currentPassword = current_password_input.text.toString(),
//                newPassword = new_password_input.text.toString(),
//                newPasswordRepeat = new_password_repeat_input.text.toString()
//            )
//        }
//
//        viewModel.event.observe(viewLifecycleOwner, Observer {
//            it.getContentIfNotHandled()?.let { event ->
//                when (event) {
//                    UpdatePasswordViewModel.ViewEvent.SUCCESS -> {
//                        showMessageAboveBottomNav(R.string.password_updated)
//                        navController.popBackStack()
//                    }
//                    UpdatePasswordViewModel.ViewEvent.FAILURE -> {
//                        showMessage(R.string.error)
//                    }
//                }
//            }
//        })
//
//        viewModel.state.observe(viewLifecycleOwner, Observer {
//            it?.let { state ->
//                when (state) {
//                    UpdatePasswordViewModel.ViewState.DEFAULT -> {
//                        progress_bar.visibility = View.GONE
//                    }
//                    UpdatePasswordViewModel.ViewState.LOADING -> {
//                        progress_bar.visibility = View.VISIBLE
//                    }
//                }
//            }
//        })
//
//        viewModel.inputValidator.observe(viewLifecycleOwner, Observer { inputValidator ->
//            current_password_input_layout.error =
//                inputValidator.currentPasswordInputErrorResId?.let { getString(it) }
//
//            new_password_input_layout.error =
//                inputValidator.newPasswordInputErrorResId?.let { getString(it) }
//
//            new_password_repeat_input_layout.error =
//                inputValidator.newPasswordRepeatInputErrorResId?.let { getString(it) }
//        })

    }
}