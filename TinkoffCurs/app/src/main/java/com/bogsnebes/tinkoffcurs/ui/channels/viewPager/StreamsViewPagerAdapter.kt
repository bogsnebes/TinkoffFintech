package com.bogsnebes.tinkoffcurs.ui.channels.viewPager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class StreamsViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> StreamsFragment.newInstance()
            else -> StreamsFragment.newInstance()
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Subscribed"
            else -> "All streams"
        }
    }
}