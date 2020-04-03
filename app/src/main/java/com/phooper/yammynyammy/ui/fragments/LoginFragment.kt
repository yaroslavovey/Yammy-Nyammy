package com.phooper.yammynyammy.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.utils.setHideErrorOnTextChangedListener
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.ext.android.inject
import timber.log.Timber

class LoginFragment : BaseFragment() {
    override val layoutRes = R.layout.fragment_login

    private val REQUEST_CODE = 3232

    private lateinit var navController: NavController

    private val googleSignInClient: GoogleSignInClient by inject()
    private val firebaseAuth: FirebaseAuth by inject()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navController = findNavController()
        initViews()
    }

    private fun initViews() {
        setHideErrorOnTextChangedListener(
            Pair(password_input_layout, password_input),
            Pair(email_input_layout, email_input)
        )
        password_input.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (areInputsFilled()) {
                    authWithEmailAndPassword(
                        email_input.text.toString(),
                        password_input.text.toString()
                    )
                }
                return@setOnEditorActionListener true
            }
            false
        }

        login_btn.setOnClickListener {
            if (areInputsFilled()) {
                authWithEmailAndPassword(
                    email_input.text.toString(),
                    password_input.text.toString()
                )
            }
        }

        login_via_google.setOnClickListener {
            startActivityForResult(googleSignInClient.signInIntent, REQUEST_CODE)
        }

        register_btn.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun areInputsFilled(): Boolean {
        when {
            (email_input.text.toString().isEmpty() && password_input.text.toString()
                .isEmpty()) -> {
                password_input_layout.error = getString(R.string.fill_password)
                email_input_layout.error = getString(R.string.fill_email)
                return false
            }
            (email_input.text.toString().isEmpty()) -> {
                email_input_layout.error = getString(R.string.fill_email)
                return false
            }
            (password_input.text.toString().isEmpty()) -> {
                password_input_layout.error = getString(R.string.fill_password)
                return false
            }
            else -> {
                return true
            }
        }
    }

    private fun authWithEmailAndPassword(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showMessage("Успех!", main_constraint_layout)
                    Timber.d("Success")
                } else {
                    showMessage("Ошибка!", main_constraint_layout)
                    Timber.d("Fail")
                }
            }
    }

    private fun authWithGoogle(signInAccount: GoogleSignInAccount?) {
        val credential =
            GoogleAuthProvider.getCredential(signInAccount?.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Timber.d("Auth success")
                    Timber.d(signInAccount?.displayName)
                } else {
                    Timber.d("Some problems")
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {
                authWithGoogle(result.signInAccount)
            }
        }
    }
}