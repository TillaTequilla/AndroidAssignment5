package com.androidAssignment5.ui.mainActivity.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.androidAssignment5.App
import com.androidAssignment5.model.Contact
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ContactsViewModel(private val application: Application) : AndroidViewModel(application) {

    private val _contactList: MutableLiveData<List<Contact>> = MutableLiveData()

    val contactList: LiveData<List<Contact>> = _contactList

    private val compositeDisposable = CompositeDisposable()

    fun getUsers(id: String, token: String) {
        viewModelScope.launch {
            compositeDisposable.add(
                (application as? App)?.appApi?.getUserContacts(id,token)
                    ?.subscribeOn(Schedulers.io())
                    ?.subscribe({
                        _contactList.postValue(it.data.contacts)
                    }, {
                    })
            )
        }
    }
    fun getListUsers() = _contactList.value

    fun deleteContact(index: Int) {
        deleteContact(_contactList.value!![index])
    }

    fun deleteContact(contact: Contact) {
        _contactList.value = _contactList.value?.minus(contact)
    }

    fun addContact(contact: Contact) {
        _contactList.value = _contactList.value?.plus(contact)
    }

    fun deleteContact(list: List<Contact>) {
        _contactList.value = _contactList.value?.minus(list.toSet())
    }
}