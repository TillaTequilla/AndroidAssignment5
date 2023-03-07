package com.androidAssignment5.ui.mainActivity.fragments.addContact

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.androidAssignment5.App
import com.androidAssignment5.model.Contact
import kotlinx.coroutines.launch

class DialogFragmentAddContactViewModel(private val app: Application) :
    AndroidViewModel(app) {


    private val _usersList: MutableLiveData<List<Contact>> = MutableLiveData()

    val usersList: LiveData<List<Contact>> = _usersList
    fun setData(token: String) {

        viewModelScope.launch {
            viewModelScope.launch {
                try {
                    val response = ((app as? App)?.appApi?.getAllUsers(token))
                    _usersList.postValue(response!!.data.users)
                } catch (_: Exception) {
                }
            }
        }
    }
}