package com.androidAssignment5.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(
//    @SerializedName("email")
    val image: String,

//    @SerializedName("name")
    val name: String,

//    @SerializedName("password")
    val password: String,

//    @SerializedName("career")
    val career: String = "",

//    @SerializedName("email")
    val email: String = "",

//    @SerializedName("phone")
    val phone: String = "",

//    @SerializedName("address")
    val address: String = "",

//    @SerializedName("birthday")
    val birthday: String = "",

    val id: String = ""
) : Parcelable {

    override fun hashCode(): Int {
        return id.toInt()
    }
}
