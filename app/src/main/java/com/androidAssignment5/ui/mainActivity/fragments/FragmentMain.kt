package com.androidAssignment5.ui.mainActivity.fragments

import android.os.Bundle
import android.view.View
import com.androidAssignment5.ui.mainActivity.adapters.ViewPagerAdapter
import com.androidAssignment5.architecture.BaseFragment
import com.androidAssignment5.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayoutMediator


class FragmentMain : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tb, binding.viewPager) { tab, pos ->
            tab.text =
                when (pos) {
                    0 -> "Profile"
                    else -> "Contacts"
                }
        }.attach()
    }
}