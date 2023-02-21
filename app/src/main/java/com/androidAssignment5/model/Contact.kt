package com.androidAssignment5.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(
    val imageURL: String,
    val name: String,
    val career: String = "",
    val eMail: String = "",
    val phone: String = "",
    val address: String = "",
    val birth: String = "",
    val id: String = name.first() + ((0..50000).random()).toString() + name.last()
) : Parcelable
