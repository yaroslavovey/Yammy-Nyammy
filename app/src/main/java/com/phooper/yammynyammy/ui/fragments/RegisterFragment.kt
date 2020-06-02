package com.phooper.yammynyammy.ui.fragments

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.utils.hideKeyboard
import com.phooper.yammynyammy.viewmodels.LoginViewModel
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : BaseFragment() {

    override val layoutRes = R.layout.fragment_register
    private lateinit var navController: NavController
    private val viewModel by activityViewModels<LoginViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navController = findNavController()
        initViews()
    }

    private fun initViews() {
        close_btn.setOnClickListener {
            navController.navigateUp()
        }

        register_btn.setOnClickListener {
            register()
        }

        password_repeat_input.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                register()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun register() {
        if (areSomeInputsEmpty()) {
            showFillFieldsError()
            return
        }
        hideKeyboard()
//        viewModel.handleRegister()
    }

    private fun areSomeInputsEmpty() =
        email_input.text.toString().isEmpty() ||
                password_input.text.toString().isEmpty() ||
                name_input.text.toString().isEmpty() ||
                password_repeat_input.text.toString().isEmpty()

    private fun showFillFieldsError() {
        if (email_input.text.toString().isEmpty())
            email_input_layout.error =
                getString(R.string.fill_email)

        if (password_input.text.toString().isEmpty())
            password_input_layout.error = getString(R.string.fill_password)

        if (password_repeat_input.text.toString().isEmpty())
            password_repeat_input_layout.error = getString(R.string.fill_password)

        if (name_input.text.toString().isEmpty())
            name_input_layout.error = getString(R.string.fill_name)

    }
}
