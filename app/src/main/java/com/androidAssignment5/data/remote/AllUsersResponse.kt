package com.androidAssignment5.data.remote

import com.androidAssignment5.model.Contact

class AllUsersResponse(
    val status: String,
    val code: Int,
    val message: String?,
    val data: Users
)
data class Users(
    val users: List<Contact>
)