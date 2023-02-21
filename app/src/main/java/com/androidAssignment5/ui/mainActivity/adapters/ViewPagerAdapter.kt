package com.androidAssignment5.ui.mainActivity.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.androidAssignment5.ui.mainActivity.fragments.ContactsFragment
import com.androidAssignment5.ui.mainActivity.fragments.ProfileFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter((fragment)) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ProfileFragment()
            else -> ContactsFragment()
        }
    }
}