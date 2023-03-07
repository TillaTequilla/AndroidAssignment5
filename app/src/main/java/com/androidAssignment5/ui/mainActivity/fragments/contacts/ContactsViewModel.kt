package com.androidAssignment5.ui.mainActivity.fragments.contacts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.androidAssignment5.App
import com.androidAssignment5.data.remote.requests.AddContactRequest
import com.androidAssignment5.model.Contact
import kotlinx.coroutines.launch

class ContactsViewModel(private val app: Application) : AndroidViewModel(app) {

    private val _contactList: MutableLiveData<List<Contact>> = MutableLiveData()

    val contactList: LiveData<List<Contact>> = _contactList


    fun getUsers(id: String, token: String) {
        viewModelScope.launch {
            try {
                val response = ((app as? App)?.appApi?.getUserContacts(id, token))
                _contactList.postValue(response!!.data.contacts)
            } catch (_: Exception) {
            }
        }
    }

    fun deleteContact(userId: String, contactId: String, token: String) {
        viewModelScope.launch {
            try {
                val response = ((app as? App)?.appApi?.deleteContact(userId, contactId, token))
                _contactList.postValue(response!!.data.contacts)
            } catch (_: Exception) {
            }
        }
    }

    fun addContact(userId: String, token: String, id: String) {
        val request = AddContactRequest(id)
        viewModelScope.launch {
            try {
                val response = ((app as? App)?.appApi?.addContact(userId, token, request))
                _contactList.postValue(response!!.data.contacts)
            } catch (_: Exception) {
            }
        }
    }

    fun deleteSelected(list: List<Contact>, userId: String, token: String) {
        for (contact in list) {
            deleteContact(userId, contact.id, token)
        }
    }
}

