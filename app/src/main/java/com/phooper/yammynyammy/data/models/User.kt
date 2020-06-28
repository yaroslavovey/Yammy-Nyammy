package com.phooper.yammynyammy.data.models

data class User(
    val email: String = "",
    val phoneNum: String = "",
    val name: String = "",
    val addresses: List<Address>? = null
)