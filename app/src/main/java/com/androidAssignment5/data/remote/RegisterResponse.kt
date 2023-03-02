package com.androidAssignment5.data.remote

import com.androidAssignment5.model.Contact

data class RegisterResponse(
    val status: String,
    val code: Int,
    val message: String?,
    val data: ResponseBody

)
data class ResponseBody(
    val user: Contact,
    val accessToken: String,
    val refreshToken: String
)