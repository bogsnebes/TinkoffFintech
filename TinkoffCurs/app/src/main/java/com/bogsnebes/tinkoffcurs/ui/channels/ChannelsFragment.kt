package com.bogsnebes.tinkoffcurs.ui.channels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bogsnebes.tinkoffcurs.R
import com.bogsnebes.tinkoffcurs.ui.channels.viewPager.StreamsViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ChannelsFragment : Fragment() {
    private lateinit var viewModel: ChannelsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.channels_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ChannelsViewModel::class.java)
        val search: EditText = view.findViewById(R.id.searchPeopleEt)
        search.doAfterTextChanged {
            viewModel.searchStream(it.toString())
        }
        val viewPager: ViewPager2 = view.findViewById(R.id.viewPager)
        val tabLayout: TabLayout = view.findViewById(R.id.tabLayout)
        viewPager.adapter = StreamsViewPagerAdapter(parentFragmentManager, lifecycle)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.subscribed)
                    viewModel.subscribedFragment = false
                }
                else -> {
                    tab.text = getString(R.string.all_streams)
                    viewModel.subscribedFragment = true
                }
            }
        }.attach()
    }

    companion object {
        const val TAG = "ChannelsFragment"
        fun newInstance() = ChannelsFragment()
    }
}