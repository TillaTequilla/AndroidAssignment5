package com.androidAssignment5.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(
    val image: String,
    val name: String,
    val password: String,
    val career: String = "",
    val email: String = "",
    val phone: String = "",
    val address: String = "",
    val birthday: String = "",
    val id: String = name.first() + ((0..50000).random()).toString() + name.last()
) : Parcelable
