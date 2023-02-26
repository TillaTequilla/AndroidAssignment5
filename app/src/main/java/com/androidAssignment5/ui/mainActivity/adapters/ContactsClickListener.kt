package com.androidAssignment5.ui.mainActivity.adapters

import com.androidAssignment5.model.Contact

interface ContactsClickListener {
    fun onDeleteClick(contact: Contact)
    fun onContactClick(contact: Contact)
}