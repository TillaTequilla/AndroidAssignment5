package com.androidAssignment5.util


import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper(context: Context) {

    companion object {
        private const val APP_PREFERENCES = "app_preferences"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

    fun putBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun getString(key: String): String? {
        return sharedPreferences.getString(key, "")
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }


}