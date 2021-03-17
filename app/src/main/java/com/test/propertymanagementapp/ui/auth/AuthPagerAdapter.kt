package com.test.propertymanagementapp.ui.auth

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.test.propertymanagementapp.data.models.enums.AccountType

class AuthPagerAdapter(val fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val mData = listOf(AccountType.tenant,AccountType.landlord, AccountType.manager, AccountType.vendor)
        .map{Pair(it,RegisterFragment.newInstance(it))}
    override fun getCount(): Int {
        return mData.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mData[position].first.toString()
    }

    override fun getItem(position: Int): Fragment {
        return mData[position].second
    }
}