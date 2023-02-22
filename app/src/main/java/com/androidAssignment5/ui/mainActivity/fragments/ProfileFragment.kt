package com.androidAssignment5.ui.mainActivity.fragments

import android.os.Bundle
import android.view.View

import androidx.viewpager2.widget.ViewPager2
import com.androidAssignment5.R
import com.androidAssignment5.architecture.BaseFragment
import com.androidAssignment5.databinding.FragmentProfileBinding
import com.androidAssignment5.util.Constance


class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        binding.run {
            btnContacts.setOnClickListener {
                viewPager?.currentItem = 1
            }
        }
        binding.tvName.text = requireActivity().intent.getStringExtra(Constance.INTENT_NAME)
    }
}