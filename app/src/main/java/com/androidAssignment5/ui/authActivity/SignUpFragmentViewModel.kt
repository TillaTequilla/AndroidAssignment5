package com.androidAssignment5.ui.authActivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.androidAssignment5.data.api.AppApi
import com.androidAssignment5.data.remote.RegisterRequest
import com.androidAssignment5.data.remote.RegisterResponse
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class SignUpFragmentViewModel(private val app: Application) : AndroidViewModel(app) {

    private val compositeDisposable = CompositeDisposable()

    private val _response = MutableLiveData<RegisterResponse>()
    val response: LiveData<RegisterResponse> = _response
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
                        _response.postValue(it)
                    }, {
                    })
            )
        }
    }
}