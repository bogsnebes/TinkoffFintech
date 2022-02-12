package com.bogsnebes.tinkoffcurs.ui.channels.viewPager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bogsnebes.tinkoffcurs.R
import com.bogsnebes.tinkoffcurs.ui.channels.ChannelsViewModel
import com.bogsnebes.tinkoffcurs.ui.channels.viewPager.recycler.StreamsRecyclerAdapter

class StreamsFragment : Fragment() {
    private lateinit var viewModel: ChannelsViewModel
    private lateinit var streamsAdapter: StreamsRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.streams_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.channelsRv)
        val progressBar: ProgressBar = view.findViewById(R.id.profileProgressBar)
        viewModel = ViewModelProvider(requireActivity()).get(ChannelsViewModel::class.java)
        streamsAdapter = StreamsRecyclerAdapter(view.context, mutableListOf(),
            callbackStream = { _, holder ->
                if (holder.recyclerView.visibility == View.VISIBLE) {
                    holder.recyclerView.visibility = View.GONE
                    holder.arrow.load(R.drawable.ic_arrow_drop_down)
                } else {
                    holder.recyclerView.visibility = View.VISIBLE
                    holder.arrow.load(R.drawable.ic_arrow_drop_up)
                }
            },
            callbackChat = {
                parentFragmentManager.setFragmentResult(
                    CHAT_OPEN_KEY,
                    bundleOf(CHAT_OPEN_KEY to it)
                )
            })
        recyclerView.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = streamsAdapter
        }
        viewModel.streamsScreenState.observe(viewLifecycleOwner) {
            when (it) {
                is StreamsScreenState.Result -> {
                    streamsAdapter.setItems(it.items)
                    recyclerView.scrollToPosition(0)
                    progressBar.isVisible = false
                }
                is StreamsScreenState.Loading -> {
                    progressBar.isVisible = true
                }
                else -> {
                    Toast.makeText(view.context, getString(R.string.error), Toast.LENGTH_SHORT).show()
                    progressBar.isVisible = false
                }
            }
        }
    }

    companion object {
        const val CHAT_OPEN_KEY = "CHAT_OPEN_KEY"

        fun newInstance() = StreamsFragment()
    }
}