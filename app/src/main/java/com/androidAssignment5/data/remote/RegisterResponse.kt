package com.androidAssignment5.data.remote

import com.androidAssignment5.model.Contact

data class RegisterResponse(
    val user: Contact,
    val accessToken: String,
    val refreshToken: String
)