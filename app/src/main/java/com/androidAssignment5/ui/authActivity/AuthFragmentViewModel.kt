package com.androidAssignment5.ui.authActivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.androidAssignment5.data.remote.AppApi
import io.reactivex.disposables.CompositeDisposable

class AuthFragmentViewModel(private val application: Application) : AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun registerUser(
        email: String,
        password: String,
        name: String,
        phone: String
    ) {
        
    }
}