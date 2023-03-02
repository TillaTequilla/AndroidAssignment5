package com.androidAssignment5.ui.mainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {

    private val _id: MutableLiveData<String> = MutableLiveData()

    val id: LiveData<String> = _id

    private val _token: MutableLiveData<String> = MutableLiveData()

    private val token: LiveData<String> = _token

    fun setId(id:String){
        _id.value=id
    }

    fun setToken(token:String){
        _token.value=token
    }

    fun getId(): String {
        return id.value.toString()
    }

    fun getToken(): String {
        return token.value.toString()
    }
}