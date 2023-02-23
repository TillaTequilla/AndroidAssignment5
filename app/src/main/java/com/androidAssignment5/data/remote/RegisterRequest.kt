package com.androidAssignment5.data.remote

data class RegisterRequest(
    val password: String,
    val name: String,
    val phone: String = "",
    val address: String = "",
    val career: String = "",
    val birthday: String = "",
    val facebook: String = "",
    val instagram: String = "",
    val twitter: String = "",
    val linkedin: String = "",
    val image: String = "",
) {
}