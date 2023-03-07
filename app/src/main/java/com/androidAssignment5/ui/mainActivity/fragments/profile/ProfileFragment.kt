package com.androidAssignment5.ui.mainActivity.fragments.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels

import androidx.viewpager2.widget.ViewPager2
import com.androidAssignment5.R
import com.androidAssignment5.architecture.BaseFragment
import com.androidAssignment5.databinding.FragmentProfileBinding
import com.androidAssignment5.extension.addCircularImage
import com.androidAssignment5.ui.mainActivity.MainActivityViewModel


class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {


    private val profileFragmentViewModel: ProfileFragmentViewModel by activityViewModels()
    private val activityViewModel: MainActivityViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setDataToUi()

    }

    private fun setListeners() {
        profileFragmentViewModel.user.observe(viewLifecycleOwner) {
            binding.tvName.text = it.name
            binding.tvAddress.text = it.address
            binding.tvCareer.text = it.birthday
            if (it.image != null) {
                binding.ivPhotoProfile.addCircularImage(it.image)
            }
        }

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        binding.run {
            btnContacts.setOnClickListener {
                viewPager?.currentItem = 1
            }
        }
    }

    private fun setDataToUi() {
        profileFragmentViewModel.setData(activityViewModel.getId(), activityViewModel.getToken())
    }


}