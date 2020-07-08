package com.phooper.yammynyammy.ui.fragments

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.utils.hideKeyboard
import com.phooper.yammynyammy.utils.isEmailValid
import com.phooper.yammynyammy.utils.setHideLayoutErrorOnTextChangedListener
import com.phooper.yammynyammy.viewmodels.LoginViewModel
import kotlinx.android.synthetic.main.fragment_register.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RegisterFragment : BaseFragment() {

    override val layoutRes = R.layout.fragment_register

    private lateinit var navController: NavController

    private val sharedViewModel by sharedViewModel<LoginViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navController = findNavController()
        initViews()
    }

    private fun initViews() {
        email_input.setHideLayoutErrorOnTextChangedListener(email_input_layout)
        password_input.setHideLayoutErrorOnTextChangedListener(password_input_layout)
        password_repeat_input.setHideLayoutErrorOnTextChangedListener(password_repeat_input_layout)

        close_btn.setOnClickListener {
            navController.navigateUp()
        }

        register_btn.setOnClickListener {
            signUp()
        }

        password_repeat_input.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                signUp()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun signUp() {
        if (!email_input.text.toString().isEmailValid()) {
            email_input_layout.error = getString(R.string.invalid_email)
            return
        }

        if (areSomeInputsEmpty()) {
            showFillFieldsError()
            return
        }

        if (!doesPasswordsMatch()) {
            showPasswordsDoesNotMatchError()
            return
        }

        if (arePasswordsTooShort()) {
            showPasswordsTooShortError()
            return
        }

        hideKeyboard()

        sharedViewModel.handleSignUpViaEmail(
            email = email_input.text.toString(),
            password = password_input.text.toString()
        )
    }

    private fun areSomeInputsEmpty() =
        email_input.text.toString().isEmpty() ||
                password_input.text.toString().isEmpty() ||
                password_repeat_input.text.toString().isEmpty()

    private fun showFillFieldsError() {
        if (email_input.text.toString().isEmpty())
            email_input_layout.error =
                getString(R.string.fill_email)

        if (password_input.text.toString().isEmpty())
            password_input_layout.error = getString(R.string.fill_password)

        if (password_repeat_input.text.toString().isEmpty())
            password_repeat_input_layout.error = getString(R.string.fill_password)

    }

    private fun doesPasswordsMatch() =
        password_input.text.toString() ==
                password_repeat_input.text.toString()

    private fun showPasswordsDoesNotMatchError() {
        password_input_layout.error = getString(R.string.passwords_does_not_match)
        password_repeat_input_layout.error = getString(R.string.passwords_does_not_match)
    }

    private fun arePasswordsTooShort() =
        password_input.text.toString().length < 7

    private fun showPasswordsTooShortError() {
        password_input_layout.error = getString(R.string.passwords_are_to_short)
        password_repeat_input_layout.error = getString(R.string.passwords_are_to_short)
    }
}
