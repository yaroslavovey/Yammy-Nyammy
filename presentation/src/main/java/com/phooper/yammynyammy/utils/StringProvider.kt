package com.phooper.yammynyammy.utils

import android.content.Context

class StringProvider(private val context: Context) {

    fun getString(resId: Int): String = context.resources.getString(resId)

}