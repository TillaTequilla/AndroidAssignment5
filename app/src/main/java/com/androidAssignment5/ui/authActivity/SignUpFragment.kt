package com.androidAssignment5.ui.authActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.androidAssignment5.App
import com.androidAssignment5.R
import com.androidAssignment5.architecture.BaseFragment
import com.androidAssignment5.databinding.FragmentSignUpBinding
import com.androidAssignment5.extension.toast
import com.androidAssignment5.ui.mainActivity.MainActivity
import com.androidAssignment5.util.Constance


class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {

    private val signUpViewModel: SignUpFragmentViewModel by activityViewModels()


    private val args: SignUpFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val email = args.email
        val password = args.password

        signUpViewModel.response.observe(viewLifecycleOwner) {
            val intent = Intent(requireActivity(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            intent.putExtra(Constance.INTENT_ID, signUpViewModel.response.value?.data?.user?.id)
            intent.putExtra(
                Constance.INTENT_ACCESS_TOKEN,
                signUpViewModel.response.value?.data?.accessToken
            )
            startActivity(intent)
            requireActivity().finish()
        }


        binding.tvForward.setOnClickListener {
            if (binding.etNewUserName.text!!.isEmpty()) {
                context?.toast("At least name")
            } else {
                val name = binding.etNewUserName.text.toString()
                val phone = binding.etNewUserPhone.text.toString()
                signUpViewModel.registerUser(
                    (requireActivity().application as? App)?.appApi,
                    email,
                    password,
                    name,
                    phone
                )
            }
        }
        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}