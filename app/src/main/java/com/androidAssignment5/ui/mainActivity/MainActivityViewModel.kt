package com.androidAssignment5.ui.mainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {

    private val _userId: MutableLiveData<String> = MutableLiveData()

    val userId: LiveData<String> = _userId

    private val _token: MutableLiveData<String> = MutableLiveData()

    private val token: LiveData<String> = _token

    fun setId(id:String){
        _userId.value=id
    }

    fun setToken(token:String){
        _token.value=token
    }

    fun getId(): String {
        return userId.value.toString()
    }

    fun getToken(): String {
        return token.value.toString()
    }
}