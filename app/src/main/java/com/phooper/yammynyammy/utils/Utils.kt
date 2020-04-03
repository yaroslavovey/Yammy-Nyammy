package com.phooper.yammynyammy.utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

fun setHideErrorOnTextChangedListener(vararg layoutsAndEdiTextPairs: Pair<TextInputLayout, TextInputEditText>) {
    layoutsAndEdiTextPairs.forEach {
        it.second.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                it.first.error = null
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }

}