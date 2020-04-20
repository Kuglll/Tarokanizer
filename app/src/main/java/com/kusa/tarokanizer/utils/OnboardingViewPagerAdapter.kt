package com.kusa.tarokanizer.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class OnboardingViewPagerAdapter internal constructor(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    val list = mutableListOf<Fragment>()

    fun addFragment(fragment: Fragment) {
        list.add(fragment)
    }

    override fun getItem(position: Int) = list[position]

    override fun getCount() = list.size

}