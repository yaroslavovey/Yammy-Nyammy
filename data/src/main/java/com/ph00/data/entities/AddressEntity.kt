package com.ph00.data.entities

import com.google.firebase.firestore.DocumentId

data class AddressEntity(
    @DocumentId val uid: String = "",
    val street: String = "",
    val houseNum: String = "",
    val apartNum: String = ""
)