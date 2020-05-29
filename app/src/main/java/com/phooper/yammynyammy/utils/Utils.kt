package com.phooper.yammynyammy.utils

import android.text.Editable
import android.text.TextWatcher
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