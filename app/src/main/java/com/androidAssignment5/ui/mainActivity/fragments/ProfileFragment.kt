package com.androidAssignment5.ui.mainActivity.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels

import androidx.viewpager2.widget.ViewPager2
import com.androidAssignment5.R
import com.androidAssignment5.architecture.BaseFragment
import com.androidAssignment5.databinding.FragmentProfileBinding
import com.androidAssignment5.ui.authActivity.AuthFragmentViewModel
import com.androidAssignment5.util.Constance
import com.androidAssignment5.util.PreferenceHelper


class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {


    private val profileFragmentViewModel: ProfileFragmentViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = requireActivity().intent.getStringExtra(Constance.INTENT_ID).toString()
        val token =
            requireActivity().intent.getStringExtra(Constance.INTENT_ACCESS_TOKEN).toString()
        profileFragmentViewModel.user.observe(viewLifecycleOwner) {
            binding.tvName.text=it.name
            binding.tvAddress.text=it.address
            binding.tvCareer.text=it.birthday
        }
        profileFragmentViewModel.setData(id,"Bearer " + token)
        println(token)
        println(token)
        println(token)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        binding.run {
            btnContacts.setOnClickListener {
                viewPager?.currentItem = 1
            }
        }
    }
}