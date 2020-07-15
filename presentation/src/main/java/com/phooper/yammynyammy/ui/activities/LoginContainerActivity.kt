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
import com.phooper.yammynyammy.viewmodels.LoginContainerViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel

@FlowPreview
@ExperimentalCoroutinesApi
class LoginContainerActivity : AppCompatActivity() {

    private val viewModel by viewModel<LoginContainerViewModel>()

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_container)

        navController = findNavController(R.id.login_nav_host_fragment)

        viewModel.event.observe(this, Observer {
            it.getContentIfNotHandled()?.let { loginEvent ->
                when (loginEvent) {
                    LoginContainerViewModel.ViewEvent.NAVIGATE_TO_MAIN_ACTIVITY -> {
                        startActivity(Intent(this, MainContainerActivity::class.java))
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                        finish()
                    }
                }
            }
        })
    }
}