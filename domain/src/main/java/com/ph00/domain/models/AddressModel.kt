package com.ph00.domain.models

import com.google.firebase.firestore.DocumentId

data class AddressModel(
    @DocumentId val uid: String = "",
    val street: String = "",
    val houseNum: String = "",
    val apartNum: String = ""
)