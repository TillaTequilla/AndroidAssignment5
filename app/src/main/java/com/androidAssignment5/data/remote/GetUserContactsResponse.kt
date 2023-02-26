package com.androidAssignment5.data.remote

import com.androidAssignment5.model.Contact

data class GetUserContactsResponse (
    val status: String,
    val code: Int,
    val message: String?,
    val data: Contacts
)

data class Contacts(
    val contacts: List<Contact>
)