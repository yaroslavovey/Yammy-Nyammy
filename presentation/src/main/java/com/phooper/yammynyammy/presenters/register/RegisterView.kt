package com.phooper.yammynyammy.presenters.register

import com.phooper.yammynyammy.presenters.login.LoginView
import moxy.viewstate.strategy.alias.Skip

interface RegisterView : LoginView {


    @Skip
    fun setPasswordRepeatInputError(errorMsg: String)

}