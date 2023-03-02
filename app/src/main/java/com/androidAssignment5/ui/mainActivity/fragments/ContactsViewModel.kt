package com.androidAssignment5.ui.mainActivity.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.androidAssignment5.App
import com.androidAssignment5.data.remote.AddContactRequest
import com.androidAssignment5.model.Contact
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ContactsViewModel(private val app: Application) : AndroidViewModel(app) {

    private val _contactList: MutableLiveData<List<Contact>> = MutableLiveData()

    val contactList: LiveData<List<Contact>> = _contactList

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun getUsers(id: String, token: String) {
        viewModelScope.launch {
            compositeDisposable.add(
                (app as? App)?.appApi?.getUserContacts(id, token)
                    ?.subscribeOn(Schedulers.io())
                    ?.subscribe({
                        _contactList.postValue(it.data.contacts)
                    }, {
                    })
            )
        }
    }

    fun deleteContact(userId: String, contactId: String, token: String) {
        viewModelScope.launch {
            compositeDisposable.add(
                (app as? App)?.appApi?.deleteContact(userId, contactId, token)
                    ?.subscribeOn(Schedulers.io())
                    ?.subscribe({
                        _contactList.postValue(it.data.contacts)
                    }, {
                    })
            )
        }
    }

    fun addContact(userId: String, token: String, id: String) {
        viewModelScope.launch {
            val request = AddContactRequest(id)
            compositeDisposable.add(
                (app as? App)?.appApi?.addContact(userId, token, request)
                    ?.subscribeOn(Schedulers.io())
                    ?.subscribe({
                        _contactList.postValue(it.data.contacts)
                    }, {
                    })
            )
        }
    }

    fun deleteSelected(list: List<Contact>, userId: String, token: String) {
        for (contact in list) {
            deleteContact(userId, contact.id, token)
        }
    }


}