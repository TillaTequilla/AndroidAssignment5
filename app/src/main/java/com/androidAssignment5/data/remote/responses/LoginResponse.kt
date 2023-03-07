package com.androidAssignment5.data.remote.responses

import com.androidAssignment5.model.Contact

data class LoginResponse(
    val status: String,
    val code: Int,
    val message: String?,
    val data: AuthorizeResponseBody
)

data class AuthorizeResponseBody(
    val user: Contact,
    val accessToken: String,
    val refreshToken: String
)