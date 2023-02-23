package com.androidAssignment5.ui.authActivity


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.androidAssignment5.util.Constance
import com.androidAssignment5.util.PreferenceHelper
import com.androidAssignment5.R
import com.androidAssignment5.architecture.BaseFragment
import com.androidAssignment5.databinding.FragmentAuthBinding
import com.androidAssignment5.ui.mainActivity.MainActivity
import com.androidAssignment5.util.NameParser


class AuthFragment : BaseFragment<FragmentAuthBinding>(FragmentAuthBinding::inflate) {
    private lateinit var preferenceHelper: PreferenceHelper


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        preferenceHelper = PreferenceHelper(this.requireActivity())
        super.onViewCreated(view, savedInstanceState)
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

            tvSignIn.setOnClickListener {
                if (cbRememberMe.isChecked) {
                    rememberInformation()
                } else preferenceHelper.clear()
                if (checkForInput()) {
                    val name: String = getName()
                    val intent = Intent(requireActivity(), MainActivity::class.java)
                    intent.putExtra(Constance.INTENT_NAME, name)
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
            btnRegister.setOnClickListener {
                findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToSignUpFragment())
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