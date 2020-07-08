package com.phooper.yammynyammy.entities

import com.livermor.delegateadapter.delegate.diff.KDiffUtilItem

data class Address(
    override val id: String,
    val street: String,
    val houseNum: String,
    val apartNum: String
) : KDiffUtilItem