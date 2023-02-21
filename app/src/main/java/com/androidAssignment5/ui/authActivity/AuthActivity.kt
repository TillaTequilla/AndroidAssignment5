package com.androidAssignment5.ui.authActivity


import android.content.Intent
import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import com.androidAssignment5.util.Constance
import com.androidAssignment5.util.PreferenceHelper
import com.androidAssignment5.R
import com.androidAssignment5.architecture.BaseActivity
import com.androidAssignment5.databinding.ActivityAuthBinding
import com.androidAssignment5.ui.mainActivity.MainActivity
import com.androidAssignment5.util.NameParser


class AuthActivity : BaseActivity<ActivityAuthBinding>(ActivityAuthBinding::inflate) {
    private lateinit var preferenceHelper: PreferenceHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        preferenceHelper = PreferenceHelper(this)
        super.onCreate(savedInstanceState)
        getPreferencesData()
        listenerInitialization()
    }

    private fun listenerInitialization() {
        with(binding) {
            etPassword.doAfterTextChanged { text ->
                if (text!!.length < 5) {
                    tilPassword.error = getString(R.string.login_error_password_few_symbols)
                } else if (!text.contains("\\d".toRegex())) {
                    tilPassword.error = getString(R.string.login_error_password_number)
                } else tilPassword.error = null
            }

            etEmail.doAfterTextChanged { text ->
                if (NameParser.validEmail(text.toString())) {
                    tilEmail.error = null
                } else tilEmail.error = getString(R.string.login_error_email_valid_email)
            }

            btnRegister.setOnClickListener {
                if (cbRememberMe.isChecked) {
                    rememberInformation()
                } else preferenceHelper.clear()
                if (checkForInput()) {
                    val name: String = getName()
                    val intent = Intent(this@AuthActivity, MainActivity::class.java)
                    intent.putExtra(Constance.INTENT_NAME, name)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                }
            }
        }
    }

    private fun checkForInput(): Boolean {
        with(binding) {
            return tilEmail.error == null && tilPassword.error == null
                    && etEmail.text!!.isNotEmpty() && etPassword.text!!.isNotEmpty()
        }
    }

    private fun getPreferencesData() {
        with(binding) {
            if (
                preferenceHelper.getBoolean(
                    Constance.SHARED_PREFERENCES_REMEMBER
                )
            ) {
                etEmail.setText(
                    preferenceHelper.getString(
                        Constance.SHARED_PREFERENCES_EMAIL
                    )
                )
                etPassword.setText(
                    preferenceHelper.getString(
                        Constance.SHARED_PREFERENCES_PASSWORD
                    )
                )
                cbRememberMe.isChecked = true
            }

        }
    }

    private fun rememberInformation() {
        binding.run {
            preferenceHelper.putBoolean(
                Constance.SHARED_PREFERENCES_REMEMBER,
                cbRememberMe.isChecked
            )
            preferenceHelper.putString(
                Constance.SHARED_PREFERENCES_PASSWORD,
                etPassword.text.toString()
            )
            preferenceHelper.putString(Constance.SHARED_PREFERENCES_EMAIL, etEmail.text.toString())
        }

    }

    private fun getName(): String {
        return NameParser.getName(binding.etEmail.text.toString())
    }

}