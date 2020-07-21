package com.phooper.yammynyammy.ui.login

import android.os.Bundle
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.Screens
import com.phooper.yammynyammy.ui.global.FlowFragment
import com.phooper.yammynyammy.utils.hideAppBar
import com.phooper.yammynyammy.utils.setLaunchScreen

class LoginFlowFragment : FlowFragment() {

    override val containerId = R.id.login_flow_container

    override val layoutRes = R.layout.fragment_login_flow

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        hideAppBar()
        if (childFragmentManager.fragments.isEmpty()) {
            navigator.setLaunchScreen(Screens.Login)
        }
    }

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: super.onBackPressed()
    }
}