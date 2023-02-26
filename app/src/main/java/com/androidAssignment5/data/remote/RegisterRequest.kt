package com.androidAssignment5.data.remote

data class RegisterRequest(
    val email: String,
    val password: String,
    val name: String? = null,
    val phone: String? = null,
    val address: String? = null,
    val career: String? = null,
    val image: String? = null,
    val birthday: String? = null
)