package com.phooper.yammynyammy.ui.fragments

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.utils.hideKeyboard
import com.phooper.yammynyammy.utils.setHideLayoutErrorOnTextChangedListener
import com.phooper.yammynyammy.viewmodels.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment() {

    private lateinit var navController: NavController

    override val layoutRes = R.layout.fragment_login

    private val viewModel by activityViewModels<LoginViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navController = findNavController()
        initViews()
    }

    private fun initViews() {
        password_input.setHideLayoutErrorOnTextChangedListener(password_input_layout)
        email_input.setHideLayoutErrorOnTextChangedListener(email_input_layout)

        password_input.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                signInViaEmail()
                return@setOnEditorActionListener true
            }
            false
        }

        login_btn.setOnClickListener {
            signInViaEmail()
        }

        login_anonymously.setOnClickListener {
            viewModel.signInAnonymously()
        }

        register_btn.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun signInViaEmail() {
        if (areSomeInputsEmpty()) {
            showFillFieldsError()
            return
        }
        hideKeyboard()
        viewModel.signInViaEmail(
            email_input.text.toString(),
            password_input.text.toString()
        )
    }

    private fun areSomeInputsEmpty() =
        password_input.text.toString().isEmpty() ||
                email_input.text.toString().isEmpty()

    private fun showFillFieldsError() {
        if (email_input.text.toString().isEmpty())
            email_input_layout.error =
                getString(R.string.fill_email)

        if (password_input.text.toString().isEmpty())
            password_input_layout.error = getString(R.string.fill_password)

    }
}