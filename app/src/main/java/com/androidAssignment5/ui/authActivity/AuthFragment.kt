package com.androidAssignment5.ui.authActivity


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
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

    private val authFragmentViewModel: AuthFragmentViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        preferenceHelper = PreferenceHelper(this.requireActivity())
        super.onViewCreated(view, savedInstanceState)
        getPreferencesData()
        listenerInitialization()
    }

    private fun listenerInitialization() {
        setEditTextChangeListener()
        setLoginRegisterListener()
    }

    private fun setLoginRegisterListener() {
        with(binding) {
            tvSignIn.setOnClickListener {
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                val intent = Intent(requireActivity(), MainActivity::class.java)
                authFragmentViewModel.loginUser(
                    email,
                    password
                )
                authFragmentViewModel.error.observe(viewLifecycleOwner) {
                    checkForInput()
                }
                authFragmentViewModel.response.observe(viewLifecycleOwner) {
                    changeActivity(intent)
                }
                if (cbRememberMe.isChecked) {
                    rememberInformation()
                } else preferenceHelper.clear()
            }
            btnRegister.setOnClickListener {
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                if (tilEmail.error == null && tilPassword.error == null
                    && etEmail.text!!.isNotEmpty() && etPassword.text!!.isNotEmpty()
                ) {
                    findNavController().navigate(
                        AuthFragmentDirections.actionAuthFragmentToSignUpFragment(
                            email,
                            password
                        )
                    )
                }
            }
        }
    }

    private fun setEditTextChangeListener() {
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
        }
    }

    private fun changeActivity(intent: Intent) {
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        intent.putExtra(Constance.INTENT_ID, authFragmentViewModel.response.value?.data?.user?.id)
        intent.putExtra(
            Constance.INTENT_ACCESS_TOKEN,
            authFragmentViewModel.response.value?.data?.accessToken
        )
        startActivity(intent)
        requireActivity().finish()
    }

    private fun checkForInput(): Boolean {
        if (authFragmentViewModel.error.value == "HTTP 401 Unauthorized") {
            with(binding) {
                tilEmail.error = getString(R.string.login_error_cant_find_user)
                tilPassword.error = getString(R.string.login_error_cant_find_user)
            }
        }
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

}