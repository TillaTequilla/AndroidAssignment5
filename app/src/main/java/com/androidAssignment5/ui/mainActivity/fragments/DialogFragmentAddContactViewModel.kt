package com.androidAssignment5.ui.mainActivity.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.androidAssignment5.App
import com.androidAssignment5.data.remote.LoginResponse
import com.androidAssignment5.model.Contact
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class DialogFragmentAddContactViewModel(private val app: Application) :
    AndroidViewModel(app) {

    private val compositeDisposable = CompositeDisposable()

    private val _usersList: MutableLiveData<List<Contact>> = MutableLiveData()

    val usersList: LiveData<List<Contact>> = _usersList

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
    fun setData(token: String) {
        viewModelScope.launch {
            compositeDisposable.add(
                (app as? App)?.appApi?.getAllUsers(token)
                    ?.subscribeOn(Schedulers.io())
                    ?.subscribe({
                        _usersList.postValue(it.data.users)
                    }, {
                    })
            )
        }
    }


}