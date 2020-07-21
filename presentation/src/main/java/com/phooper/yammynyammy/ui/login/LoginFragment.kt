package com.phooper.yammynyammy.ui.login

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import com.google.android.material.snackbar.Snackbar
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.presenters.login.LoginPresenter
import com.phooper.yammynyammy.presenters.login.LoginView
import com.phooper.yammynyammy.ui.global.BaseFragment
import com.phooper.yammynyammy.utils.hideKeyboardKtx
import com.phooper.yammynyammy.utils.setHideLayoutErrorOnTextChangedListener
import kotlinx.android.synthetic.main.fragment_login.*
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class LoginFragment : BaseFragment(), LoginView {

    @Inject
    lateinit var presenterProvider: Provider<LoginPresenter>

    private val presenter by moxyPresenter { presenterProvider.get() }

    override val layoutRes = R.layout.fragment_login

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        password_input.setHideLayoutErrorOnTextChangedListener(password_input_layout)
        email_input.setHideLayoutErrorOnTextChangedListener(email_input_layout)

        password_input.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                signIn()
                return@setOnEditorActionListener true
            }
            false
        }

        login_btn.setOnClickListener {
            signIn()
        }

        register_btn.setOnClickListener {
            presenter.signUp()
        }
    }

    private fun signIn() =
        presenter.signIn(email_input.text.toString(), password_input.text.toString())

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

    override fun hideKeyboard() = hideKeyboardKtx()

    override fun onBackPressed() = presenter.onBackPressed()

//    override fun showMessage(message: String) {
//        Snackbar.make(requireView(),message, Snackbar.LENGTH_LONG).show()
//    }
}