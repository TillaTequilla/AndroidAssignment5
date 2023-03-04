package com.androidAssignment5.ui.authActivity

import android.app.Application
import android.content.Context
import android.text.Editable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.androidAssignment5.App
import com.androidAssignment5.R
import com.androidAssignment5.data.remote.LoginRequest
import com.androidAssignment5.data.remote.LoginResponse
import com.androidAssignment5.util.NameParser
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthFragmentViewModel @Inject constructor(@ActivityContext val context: Context,private val app: Application) :
    AndroidViewModel(app) {

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _response = MutableLiveData<LoginResponse>()
    val response: LiveData<LoginResponse> = _response

    fun loginUser(
        email: String,
        password: String,
    ) {
        val request = LoginRequest(email, password)
        viewModelScope.launch {
            try {
                _response.postValue((app as? App)?.appApi?.loginUser(request))
            } catch (_: Exception) {
                _error.postValue("HTTP 401 Unauthorized")
            }
        }
    }

    fun checkPassword(text: Editable?): Int {
        return if (text!!.length < 5) {
            0
        } else if (!text.contains("\\d".toRegex())) {
            1
        } else 2
    }

    fun checkEmail(text: Editable?): Int {
        return if (NameParser.validEmail(text.toString())) {
            0
        } else 1
    }

    fun context() {
    }
}