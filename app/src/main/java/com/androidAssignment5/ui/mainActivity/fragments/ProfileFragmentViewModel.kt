package com.androidAssignment5.ui.mainActivity.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.androidAssignment5.App
import com.androidAssignment5.model.Contact
import kotlinx.coroutines.launch

class ProfileFragmentViewModel(private val app: Application) :
    AndroidViewModel(app) {

    private val _user = MutableLiveData<Contact>()
    val user: LiveData<Contact> = _user

    fun setData(id: String, token: String) {
        viewModelScope.launch {
            try {
                val response = ((app as? App)?.appApi?.getUser(id, token))
                _user.postValue(response!!.data.user)
            } catch (_: Exception) {
            }
        }
    }
}