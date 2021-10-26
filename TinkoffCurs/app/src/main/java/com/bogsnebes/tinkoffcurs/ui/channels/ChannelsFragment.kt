package com.bogsnebes.tinkoffcurs.ui.channels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bogsnebes.tinkoffcurs.R

class ChannelsFragment : Fragment() {

    companion object {
        fun newInstance() = ChannelsFragment()
    }

    private lateinit var viewModel: ChannelsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.channels_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChannelsViewModel::class.java)
    }
}