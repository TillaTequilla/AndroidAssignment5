package com.androidAssignment5.util

import androidx.recyclerview.selection.ItemKeyProvider
import com.androidAssignment5.ui.mainActivity.adapters.ContactsRecyclerViewAdapter
import com.androidAssignment5.model.Contact

class KeyProvider(private val adapter: ContactsRecyclerViewAdapter) :
    ItemKeyProvider<Contact>(SCOPE_CACHED) {
    override fun getKey(position: Int): Contact? = adapter.currentList[position]
    override fun getPosition(key: Contact): Int =
        adapter.currentList.indexOfFirst { it == key }
}