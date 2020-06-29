package com.phooper.yammynyammy.data.models

data class User(
    val phoneNum: String = "",
    val name: String = "",
    val addresses: List<Address>? = null
)