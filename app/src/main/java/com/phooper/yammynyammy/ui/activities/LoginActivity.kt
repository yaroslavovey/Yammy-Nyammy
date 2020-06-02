package com.phooper.yammynyammy.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.viewmodels.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_login.*
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
                    LoginViewModel.LoginEvent.NAVIGATE_TO_MAIN_ACTIVITY -> {
                        login_fragment_layout.visibility = View.GONE
                        startActivity(Intent(this, MainContainerActivity::class.java))
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                        finish()
                    }
                    LoginViewModel.LoginEvent.NAVIGATE_TO_PHONE_FRAGMENT -> {
                        navController.navigate(R.id.action_loginFragment_to_phoneVerificationFragment)

                    }
                    LoginViewModel.LoginEvent.AUTH_ERROR -> {
                        Snackbar.make(frame_layout, R.string.auth_error, Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        })

        viewModel.state.observe(this, Observer { loginState ->
            loginState?.let {
                when (it) {
                    LoginViewModel.LoginState.LOADING -> {
                        progress_bar.visibility = View.VISIBLE
                    }
                    LoginViewModel.LoginState.DEFAULT -> {
                        progress_bar.visibility = View.GONE
                    }
                }
            }
        })
    }
}