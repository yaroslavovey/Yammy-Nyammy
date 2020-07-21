package com.phooper.yammynyammy.presenters.register

import com.ph00.domain.usecases.SignUpViaEmailAndPasswordUseCase
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.presenters.global.BasePresenter
import com.phooper.yammynyammy.utils.Constants.Companion.FLOW
import com.phooper.yammynyammy.utils.StringProvider
import com.phooper.yammynyammy.utils.SystemMessageNotifier
import com.phooper.yammynyammy.utils.addTo
import com.phooper.yammynyammy.utils.isEmailValid
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import ru.terrakok.cicerone.Router
import javax.inject.Inject
import javax.inject.Named

@InjectViewState
class RegisterPresenter @Inject constructor(
    private val signUpViaEmailAndPasswordUseCase: SignUpViaEmailAndPasswordUseCase,
    private val stringProvider: StringProvider,
    private val systemMessageNotifier: SystemMessageNotifier,
    @Named(FLOW) private val router: Router
) : BasePresenter<RegisterView>() {


    fun signUpViaEmail(email: String, password: String, passwordRepeat: String) {
        if (someFieldsAreEmpty(email, password, passwordRepeat)) {
            showFillInputsError(email, password, passwordRepeat)
            return
        }
        if (!email.isEmailValid()) {
            viewState.setEmailInputError(stringProvider.getString(R.string.invalid_email))
            return
        }
        if (password != passwordRepeat) {
            viewState.setPasswordInputError(stringProvider.getString(R.string.passwords_does_not_match))
            viewState.setPasswordRepeatInputError(stringProvider.getString(R.string.passwords_does_not_match))
            return
        }
        if (password.length < 6) {
            viewState.setPasswordInputError(stringProvider.getString(R.string.passwords_are_too_short))
            viewState.setPasswordRepeatInputError(stringProvider.getString(R.string.passwords_are_too_short))
            return
        }

        viewState.hideKeyboard()

        signUpViaEmailAndPasswordUseCase
            .execute(email, password)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { viewState.showLoading() }
            .doFinally { viewState.hideLoading() }
            .doOnError { systemMessageNotifier.sendMessage(R.string.error) }
            .subscribe()
            .addTo(compositeDisposable)

    }

    fun close() {
        router.exit()
    }

    private fun someFieldsAreEmpty(email: String, password: String, passwordRepeat: String) =
        (email.isEmpty() || password.isEmpty() || passwordRepeat.isEmpty())

    private fun showFillInputsError(email: String, password: String, passwordRepeat: String) {
        if (email.isEmpty())
            viewState.setEmailInputError(stringProvider.getString(R.string.fill_email))

        if (password.isEmpty())
            viewState.setPasswordInputError(stringProvider.getString(R.string.fill_password))

        if (passwordRepeat.isEmpty())
            viewState.setPasswordRepeatInputError(stringProvider.getString(R.string.fill_password))
    }

}