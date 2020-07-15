package com.phooper.yammynyammy.ui.fragments

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.utils.hideKeyboard
import com.phooper.yammynyammy.utils.setHideLayoutErrorOnTextChangedListener
import com.phooper.yammynyammy.utils.showMessage
import com.phooper.yammynyammy.viewmodels.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class LoginFragment : BaseFragment() {

    private lateinit var navController: NavController

    override val layoutRes = R.layout.fragment_login

    private val viewModel by viewModel<LoginViewModel>()

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

        login_btn.setOnClickListener { signInViaEmail() }

        register_btn.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }

        viewModel.state.observe(viewLifecycleOwner, Observer {
            it?.let { state ->
                when (state) {
                    LoginViewModel.ViewState.DEFAULT -> {
                        progress_bar.visibility = View.GONE
                    }
                    LoginViewModel.ViewState.LOADING -> {
                        progress_bar.visibility = View.VISIBLE
                    }
                }
            }
        })

        viewModel.event.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { event ->
                when (event) {
                    LoginViewModel.ViewEvent.ERROR -> showMessage(R.string.error)
                }
            }
        })

        viewModel.inputValidator.observe(viewLifecycleOwner, Observer { inputValidator ->
            email_input_layout.error =
                inputValidator.emailInputErrorResId?.let { getString(it) }

            password_input_layout.error =
                inputValidator.passwordInputErrorResId?.let { getString(it) }
        })

    }

    private fun signInViaEmail() {
        hideKeyboard()
        viewModel.signInViaEmail(
            email_input.text.toString(),
            password_input.text.toString()
        )
    }
}