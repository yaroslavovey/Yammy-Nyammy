package com.phooper.yammynyammy.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.utils.showMessage
import com.phooper.yammynyammy.viewmodels.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModel<LoginViewModel>()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_LoginTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        navController = findNavController(R.id.login_nav_host_fragment)

        viewModel.event.observe(this, Observer {
            it.getContentIfNotHandled()?.let { loginEvent ->
                when (loginEvent) {
                    LoginViewModel.ViewEvent.NAVIGATE_TO_MAIN_ACTIVITY -> {
                        startActivity(Intent(this, MainContainerActivity::class.java))
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                        finish()
                    }
                    LoginViewModel.ViewEvent.NAVIGATE_TO_PHONE_NAME_FRAGMENT_FROM_LOGIN -> {
                        navController.navigate(R.id.action_loginFragment_to_namePhoneVerificationFragment)
                    }
                    LoginViewModel.ViewEvent.NAVIGATE_TO_PHONE_NAME_FRAGMENT_FROM_REGISTER -> {
                        navController.navigate(R.id.action_registerFragment_to_namePhoneVerificationFragment)
                    }
                    LoginViewModel.ViewEvent.AUTH_ERROR -> {
                        showMessage(R.string.auth_error)
                    }
                }
            }
        })

        viewModel.state.observe(this, Observer { loginState ->
            loginState?.let {
                when (it) {
                    LoginViewModel.ViewState.LOADING -> {
                        progress_bar.visibility = View.VISIBLE
                    }
                    LoginViewModel.ViewState.DEFAULT -> {
                        progress_bar.visibility = View.GONE
                    }
                }
            }
        })
    }
}