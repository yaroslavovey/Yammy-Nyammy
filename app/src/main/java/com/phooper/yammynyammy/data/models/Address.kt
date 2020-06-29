package com.phooper.yammynyammy.data.models

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import com.livermor.delegateadapter.delegate.diff.DiffUtilItem

data class Address(
    @DocumentId val uid: String = "",
    val street: String = "",
    val houseNum: String = "",
    val apartNum: String = ""
) : DiffUtilItem {

    @Exclude
    override fun id(): Any {
        return street + houseNum + apartNum
    }

    @Exclude
    override fun content(): Any {
        return this
    }
}