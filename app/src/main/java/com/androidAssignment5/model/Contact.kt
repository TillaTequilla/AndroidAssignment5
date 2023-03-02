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

    val id: String = ""
) : Parcelable {

    override fun hashCode(): Int {
        return id.toInt()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Contact
        if (name != other.name) return false
        if (id != other.id) return false
        return true
    }
}
