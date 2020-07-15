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
import com.phooper.yammynyammy.viewmodels.RegisterViewModel
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class RegisterFragment : BaseFragment() {

    override val layoutRes = R.layout.fragment_register

    private lateinit var navController: NavController

    private val viewModel by viewModel<RegisterViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navController = findNavController()
        initViews()
    }

    private fun initViews() {
        email_input.setHideLayoutErrorOnTextChangedListener(email_input_layout)
        password_input.setHideLayoutErrorOnTextChangedListener(password_input_layout)
        password_input.setHideLayoutErrorOnTextChangedListener(password_repeat_input_layout)
        password_repeat_input.setHideLayoutErrorOnTextChangedListener(password_input_layout)
        password_repeat_input.setHideLayoutErrorOnTextChangedListener(password_repeat_input_layout)

        close_btn.setOnClickListener { navController.navigateUp() }

        register_btn.setOnClickListener { signUp() }

        password_repeat_input.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                signUp()
                return@setOnEditorActionListener true
            }
            false
        }

        viewModel.state.observe(viewLifecycleOwner, Observer {
            it?.let { state ->
                when (state) {
                    RegisterViewModel.ViewState.DEFAULT -> {
                        progress_bar.visibility = View.GONE
                    }
                    RegisterViewModel.ViewState.LOADING -> {
                        progress_bar.visibility = View.VISIBLE
                    }
                }
            }
        })

        viewModel.event.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { event ->
                when (event) {
                    RegisterViewModel.ViewEvent.ERROR -> showMessage(R.string.error)
                }
            }
        })

        viewModel.inputValidator.observe(viewLifecycleOwner, Observer { inputValidator ->
            email_input_layout.error =
                inputValidator.emailInputErrorResId?.let { getString(it) }

            password_input_layout.error =
                inputValidator.passwordInputErrorResId?.let { getString(it) }

            password_repeat_input_layout.error =
                inputValidator.passwordRepeatInputErrorResId?.let { getString(it) }

        })

    }

    private fun signUp() {
        hideKeyboard()
        viewModel.signUpViaEmail(
            email = email_input.text.toString(),
            password = password_input.text.toString(),
            passwordRepeat = password_repeat_input.text.toString()
        )
    }
}
