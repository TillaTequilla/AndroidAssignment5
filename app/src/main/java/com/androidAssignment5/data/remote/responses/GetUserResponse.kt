package com.androidAssignment5.data.remote.responses

import com.androidAssignment5.model.Contact

data class GetUserResponse(
    val status: String,
    val code: Int,
    val message: String?,
    val data: Profile
)

data class Profile(
    val user: Contact
)