package com.phooper.yammynyammy.presenters.login

import com.ph00.domain.usecases.SignInViaEmailAndPasswordUseCase
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.Screens
import com.phooper.yammynyammy.presenters.global.BasePresenter
import com.phooper.yammynyammy.utils.Constants.Companion.FLOW
import com.phooper.yammynyammy.utils.StringProvider
import com.phooper.yammynyammy.utils.SystemMessageNotifier
import com.phooper.yammynyammy.utils.addTo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import ru.terrakok.cicerone.Router
import javax.inject.Inject
import javax.inject.Named

@InjectViewState
class LoginPresenter @Inject constructor(
    private val signInViaEmailAndPasswordUseCase: SignInViaEmailAndPasswordUseCase,
    private val stringProvider: StringProvider,
    private val systemMessageNotifier: SystemMessageNotifier,
    @Named(FLOW) private val router: Router
) :
    BasePresenter<LoginView>() {

    fun signUp() = router.navigateTo(Screens.Register)

    fun signIn(email: String, password: String) {
        if (someFieldsAreEmpty(email, password)) {
            showFillInputsError(email, password)
            return
        }
        viewState.hideKeyboard()

        signInViaEmailAndPasswordUseCase
            .execute(email, password)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { viewState.showLoading() }
            .doFinally { viewState.hideLoading() }
            .doOnError { systemMessageNotifier.sendMessage(R.string.error) }
            .subscribe()
            .addTo(compositeDisposable)

    }

    private fun showFillInputsError(email: String, password: String) {
        if (email.isEmpty()) viewState.setEmailInputError(stringProvider.getString(R.string.fill_email))
        if (password.isEmpty()) viewState.setPasswordInputError(stringProvider.getString(R.string.fill_password))
    }

    private fun someFieldsAreEmpty(email: String, password: String): Boolean =
        (email.isEmpty() || password.isEmpty())

    fun onBackPressed() = router.exit()

}