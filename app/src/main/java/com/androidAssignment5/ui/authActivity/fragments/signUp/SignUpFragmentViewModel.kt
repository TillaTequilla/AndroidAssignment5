package com.androidAssignment5.ui.authActivity.fragments.signUp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.androidAssignment5.App
import com.androidAssignment5.data.remote.requests.RegisterRequest
import com.androidAssignment5.data.remote.responses.RegisterResponse
import kotlinx.coroutines.launch

class SignUpFragmentViewModel(private val app: Application) : AndroidViewModel(app) {

    private val _response = MutableLiveData<RegisterResponse>()
    val response: LiveData<RegisterResponse> = _response

    fun registerUser(
        email: String,
        password: String,
        name: String,
        phone: String
    ) {
        val request = RegisterRequest(email, password, name, phone)
        viewModelScope.launch {
            try {
                _response.postValue((app as? App)?.appApi?.registerUser(request))
            } catch (_: Exception) {
            }
        }
    }
}