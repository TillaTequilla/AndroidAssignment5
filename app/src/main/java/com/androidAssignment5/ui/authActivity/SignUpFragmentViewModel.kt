package com.androidAssignment5.ui.authActivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.androidAssignment5.data.remote.AppApi
import com.androidAssignment5.data.remote.RegisterRequest
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class SignUpFragmentViewModel(private val application: Application) : AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun registerUser(
        api: AppApi?,
        email: String,
        password: String,
        name: String,
        phone: String
    ) {
        val request = RegisterRequest(email, password, name, phone)
        viewModelScope.launch {
            compositeDisposable.add(
                api?.registerUser(request)
                    ?.subscribeOn(Schedulers.io())
                    ?.subscribe({
                    }, {
                    })
            )
        }
    }
}