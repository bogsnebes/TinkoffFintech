package com.bogsnebes.tinkoffcurs.ui.channels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.bogsnebes.tinkoffcurs.R
import com.bogsnebes.tinkoffcurs.ui.channels.viewPager.StreamsViewPagerAdapter
import com.google.android.material.tabs.TabLayout

class ChannelsFragment : Fragment() {
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    private lateinit var viewModel: ChannelsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.channels_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChannelsViewModel::class.java)
        viewPager = view.findViewById(R.id.viewPager)
        tabLayout = view.findViewById(R.id.tabLayout)
        viewPager.adapter = StreamsViewPagerAdapter(parentFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
    }

    companion object {
        fun newInstance() = ChannelsFragment()
    }
}