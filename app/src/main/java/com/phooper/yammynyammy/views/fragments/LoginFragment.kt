package com.phooper.yammynyammy.views.fragments

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.utils.hideKeyboard
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment() {
    override val layoutRes = R.layout.fragment_login

    private lateinit var navController: NavController

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        navController = findNavController()

        password_input.setOnEditorActionListener { view, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                view.apply {
                    clearFocus()
                    this.context.hideKeyboard(this)
                }
                return@setOnEditorActionListener true
            }
            false
        }

        login_btn.setOnClickListener {

        }

        register_btn.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }

        register_via_google.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registerViaGoogleFragment)
        }
    }

}