package com.androidAssignment5.ui.authActivity.fragments.auth

import android.app.Application
import android.text.Editable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.androidAssignment5.App
import com.androidAssignment5.data.remote.requests.LoginRequest
import com.androidAssignment5.data.remote.responses.LoginResponse
import com.androidAssignment5.util.Constance
import com.androidAssignment5.util.NameParser
import com.androidAssignment5.util.PreferenceHelper
import kotlinx.coroutines.launch

class AuthFragmentViewModel(private val app: Application) :
    AndroidViewModel(app) {

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _response = MutableLiveData<LoginResponse>()
    val response: LiveData<LoginResponse> = _response

    var preferenceHelper: PreferenceHelper = PreferenceHelper(app.applicationContext)

    fun loginUser(
        email: String,
        password: String,
    ) {
        val request = LoginRequest(email, password)
        viewModelScope.launch {
            try {
                _response.postValue((app as? App)?.appApi?.loginUser(request))
            } catch (_: Exception) {
                app.applicationContext
                _error.postValue("HTTP 401 Unauthorized")
            }
        }
    }

    fun checkPassword(text: Editable?): Int {
        return if (text!!.length < Constance.PASSWORD_MIN_LENGTH) {

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

    fun putDataToStorage(checked: Boolean, toString: String, toString1: String) {
        preferenceHelper.putBoolean(
            Constance.SHARED_PREFERENCES_REMEMBER,
            checked
        )
        preferenceHelper.putString(
            Constance.SHARED_PREFERENCES_PASSWORD,
            toString
        )
        preferenceHelper.putString(Constance.SHARED_PREFERENCES_EMAIL, toString1)
    }

    fun clearDataStore() {
        preferenceHelper.clear()
    }


}