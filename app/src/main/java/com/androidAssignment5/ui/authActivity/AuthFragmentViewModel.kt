package com.androidAssignment5.ui.authActivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.androidAssignment5.App
import com.androidAssignment5.data.remote.LoginRequest
import com.androidAssignment5.data.remote.LoginResponse
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch


class AuthFragmentViewModel(private val app: Application) : AndroidViewModel(app) {
    private val compositeDisposable = CompositeDisposable()

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _response = MutableLiveData<LoginResponse>()
    val response: LiveData<LoginResponse> = _response

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun loginUser(
        email: String,
        password: String,
    ) {
        val request = LoginRequest(email, password)
        viewModelScope.launch {
            try {
                _response.postValue((app as? App)?.appApi?.loginUser(request))
            } catch (_: Exception){
            }

//            compositeDisposable.add(
//                (app as? App)?.appApi?.loginUser(request)
//                    ?.subscribeOn(Schedulers.io())
//                    ?.subscribe({
//                        _response.postValue(it)
//                    }, {
//                        _error.postValue(it.message.toString())
//                    })
//            )
        }

    }
}