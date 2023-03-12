package com.androidAssignment5.ui.authActivity.fragments.auth


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.androidAssignment5.util.Constance
import com.androidAssignment5.R
import com.androidAssignment5.architecture.BaseFragment
import com.androidAssignment5.databinding.FragmentAuthBinding
import com.androidAssignment5.ui.mainActivity.MainActivity
import com.androidAssignment5.util.Constance.SHARED_PREFERENCES_EMAIL
import com.androidAssignment5.util.Constance.SHARED_PREFERENCES_PASSWORD
import com.androidAssignment5.util.Constance.SHARED_PREFERENCES_REMEMBER

class AuthFragment : BaseFragment<FragmentAuthBinding>(FragmentAuthBinding::inflate) {

    private val authFragmentViewModel: AuthFragmentViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataFromStore()
        println("123")
        listenerInitialization()
    }

    private fun getDataFromStore() {
        with(binding) {
            if (authFragmentViewModel.preferenceHelper.getBoolean(SHARED_PREFERENCES_REMEMBER)
            ) {
                etEmail.setText(
                    authFragmentViewModel.preferenceHelper.getString(SHARED_PREFERENCES_EMAIL)
                )
                etPassword.setText(
                    authFragmentViewModel.preferenceHelper.getString(SHARED_PREFERENCES_PASSWORD)
                )
                cbRememberMe.isChecked = true
            }
        }
    }

    private fun listenerInitialization() {
        setEditTextChangeListener()
        setEntryListeners()
    }


    private fun setEditTextChangeListener() {
        with(binding) {
            etPassword.doAfterTextChanged { text ->
                tilPassword.error = when (authFragmentViewModel.checkPassword(text)) {
                    0 -> getString(R.string.login_error_password_few_symbols)
                    1 -> getString(R.string.login_error_password_number)
                    else -> {
                        null
                    }
                }
            }

            etEmail.doAfterTextChanged { text ->
                tilEmail.error = when (authFragmentViewModel.checkEmail(text)) {
                    0 -> null
                    else -> {
                        getString(R.string.login_error_email_valid_email)
                    }
                }
            }
        }
    }

    private fun setEntryListeners() {
        with(binding) {
            authFragmentViewModel.error.observe(viewLifecycleOwner) {
                checkForInput()
            }
            val intent = Intent(requireActivity(), MainActivity::class.java)
            authFragmentViewModel.response.observe(viewLifecycleOwner) {
                changeActivity(intent)
            }
            tvSignIn.setOnClickListener {
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                authFragmentViewModel.loginUser(
                    email,
                    password
                )
                storeData()
            }
            btnRegister.setOnClickListener {
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                if (dataIsCorrect()
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


    private fun dataIsCorrect(): Boolean {
        with(binding) {
            return tilEmail.error == null && tilPassword.error == null
                    && !etEmail.text.isNullOrBlank() && !etPassword.text.isNullOrBlank()
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

    private fun checkForInput() {
        if (authFragmentViewModel.error.value == null) {
            binding.tilEmail.error == null && binding.tilPassword.error == null
        } else {
            with(binding) {
                tilEmail.error = getString(R.string.login_error_cant_find_user)
                tilPassword.error = getString(R.string.login_error_cant_find_user)
            }
        }
    }

    private fun storeData() {
        with(binding) {
            if (cbRememberMe.isChecked) {
                authFragmentViewModel.putDataToStorage(
                    cbRememberMe.isChecked,
                    etPassword.text.toString(),
                    etEmail.text.toString()
                )
            } else authFragmentViewModel.clearDataStore()
        }
    }


}