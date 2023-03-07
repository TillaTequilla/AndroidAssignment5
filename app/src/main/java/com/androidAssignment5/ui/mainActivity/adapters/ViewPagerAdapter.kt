package com.androidAssignment5.ui.mainActivity.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.androidAssignment5.ui.mainActivity.fragments.contacts.ContactsFragment
import com.androidAssignment5.ui.mainActivity.fragments.profile.ProfileFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter((fragment)) {
    override fun getItemCount(): Int {
        return Tabs.values().size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            Tabs.PROFILE.position -> ProfileFragment()
            else -> ContactsFragment()
        }
    }

}