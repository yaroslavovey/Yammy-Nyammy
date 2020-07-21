package com.phooper.yammynyammy.presenters.login

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution
import moxy.viewstate.strategy.alias.Skip

interface LoginView : MvpView {

    @AddToEndSingle
    fun showLoading()

    @AddToEndSingle
    fun hideLoading()

    @Skip
    fun setEmailInputError(errorMsg: String)

    @Skip
    fun setPasswordInputError(errorMsg: String)

    @Skip
    fun hideKeyboard()

}