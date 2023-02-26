package com.androidAssignment5.ui.mainActivity.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.androidAssignment5.App
import com.androidAssignment5.data.remote.LoginRequest
import com.androidAssignment5.model.Contact
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ProfileFragmentViewModel(private val application: Application) :
    AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()

    private val _user = MutableLiveData<Contact>()
    val user: LiveData<Contact> = _user


    fun setData(id: String, token: String) {
        viewModelScope.launch {
            compositeDisposable.add(
                (application as? App)?.appApi?.getUser(id,token)
                    ?.subscribeOn(Schedulers.io())
                    ?.subscribe({
                        _user.postValue(it.data.user)
                    }, {
                    })
            )
        }
    }
}