package com.phooper.yammynyammy.ui.register

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.presenters.register.RegisterPresenter
import com.phooper.yammynyammy.presenters.register.RegisterView
import com.phooper.yammynyammy.ui.global.BaseFragment
import com.phooper.yammynyammy.utils.hideKeyboardKtx
import com.phooper.yammynyammy.utils.setHideLayoutErrorOnTextChangedListener
import kotlinx.android.synthetic.main.fragment_register.*
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class RegisterFragment : BaseFragment(), RegisterView {

    @Inject
    lateinit var presenterProvider: Provider<RegisterPresenter>

    private val presenter by moxyPresenter { presenterProvider.get() }

    override val layoutRes = R.layout.fragment_register


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        email_input.setHideLayoutErrorOnTextChangedListener(email_input_layout)
        password_input.setHideLayoutErrorOnTextChangedListener(password_input_layout)
        password_input.setHideLayoutErrorOnTextChangedListener(password_repeat_input_layout)
        password_repeat_input.setHideLayoutErrorOnTextChangedListener(password_input_layout)
        password_repeat_input.setHideLayoutErrorOnTextChangedListener(password_repeat_input_layout)

        close_btn.setOnClickListener { presenter.close() }

        register_btn.setOnClickListener { signUp() }

        password_repeat_input.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                signUp()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun signUp() {
        presenter.signUpViaEmail(
            email = email_input.text.toString(),
            password = password_input.text.toString(),
            passwordRepeat = password_repeat_input.text.toString()
        )
    }

    override fun showLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress_bar.visibility = View.GONE
    }

    override fun setEmailInputError(errorMsg: String) {
        email_input_layout.error = errorMsg
    }

    override fun setPasswordInputError(errorMsg: String) {
        password_input_layout.error = errorMsg
    }

    override fun setPasswordRepeatInputError(errorMsg: String) {
        password_repeat_input_layout.error = errorMsg
    }

    override fun hideKeyboard() = hideKeyboardKtx()

}
