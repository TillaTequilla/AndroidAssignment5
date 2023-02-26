package com.androidAssignment5.ui.authActivity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.androidAssignment5.App
import com.androidAssignment5.architecture.BaseFragment
import com.androidAssignment5.databinding.FragmentSignUpBinding


class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {

    private val signUpViewModel: SignUpFragmentViewModel by activityViewModels()

    private val args : SignUpFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val email=args.email
        val password=args.password

        binding.tvForward.setOnClickListener {
            val name=binding.etNewUserName.text.toString()
            val phone=binding.etNewUserPhone.text.toString()
            signUpViewModel.registerUser((requireActivity().application as? App)?.appApi,email,password,name,phone)
        }
    }
}