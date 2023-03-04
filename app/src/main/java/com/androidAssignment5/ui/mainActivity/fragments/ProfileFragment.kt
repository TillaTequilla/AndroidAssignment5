package com.androidAssignment5.ui.mainActivity.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels

import androidx.viewpager2.widget.ViewPager2
import com.androidAssignment5.R
import com.androidAssignment5.architecture.BaseFragment
import com.androidAssignment5.databinding.FragmentProfileBinding
import com.androidAssignment5.extension.addCircularImage
import com.androidAssignment5.ui.authActivity.AuthFragmentViewModel
import com.androidAssignment5.ui.mainActivity.MainActivityViewModel
import com.androidAssignment5.util.Constance
import com.androidAssignment5.util.PreferenceHelper


class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {


    private val profileFragmentViewModel: ProfileFragmentViewModel by activityViewModels()
    private val activityViewModel: MainActivityViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        profileFragmentViewModel.user.observe(viewLifecycleOwner) {
            binding.tvName.text = it.name
            binding.tvAddress.text = it.address
            binding.tvCareer.text = it.birthday
            if (it.name != null) {
                binding.ivPhotoProfile.addCircularImage(it.image)
            }
        }
        profileFragmentViewModel.setData(activityViewModel.getId(), activityViewModel.getToken())

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        binding.run {
            btnContacts.setOnClickListener {
                viewPager?.currentItem = 1
            }
        }
    }
}