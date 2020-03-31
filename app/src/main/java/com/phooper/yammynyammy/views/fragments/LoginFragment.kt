package com.phooper.yammynyammy.views.fragments

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.utils.hideKeyboard
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.ext.android.inject
import timber.log.Timber

class LoginFragment : BaseFragment() {
    override val layoutRes = R.layout.fragment_login

    private lateinit var navController: NavController

    val firebaseAuth: FirebaseAuth by inject()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
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
            when {
                (email_input.text.toString().isEmpty() && password_input.text.toString()
                    .isEmpty()) -> {
                    password_input.error = "Заполните Пароль"
                    email_input.error = "Заполните Email"
                }
                (email_input.text.toString().isEmpty()) -> {

                    email_input.error = "Заполните Email"
                }
                (password_input.text.toString().isEmpty()) -> {

                    password_input.error = "Заполните Пароль"
                }
                else -> {
                    firebaseAuth.signInWithEmailAndPassword(
                        email_input.text.toString(),
                        password_input.text.toString()
                    ).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            showMessage("Успех!", it)
                            Timber.d("Success")
                            firebaseAuth.signOut()
                        } else {
                            showMessage("Ошибка!", it)
                            Timber.d("Fail")
                        }
                    }
                }
            }
        }

        register_btn.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }

    }

}