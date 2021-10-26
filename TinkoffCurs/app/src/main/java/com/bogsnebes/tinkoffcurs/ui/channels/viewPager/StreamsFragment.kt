package com.bogsnebes.tinkoffcurs.ui.channels.viewPager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bogsnebes.tinkoffcurs.R
import com.bogsnebes.tinkoffcurs.data.dto.TestData
import com.bogsnebes.tinkoffcurs.ui.channels.viewPager.recycler.StreamsRecyclerAdapter

class StreamsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_streams, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.channelsRv)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = StreamsRecyclerAdapter(view.context, TestData.testStreams) { _, holder ->
                if (holder.recyclerView.visibility == View.VISIBLE) {
                    holder.recyclerView.visibility = View.GONE
                    holder.arrow.load(R.drawable.ic_arrow_drop_down)
                } else {
                    holder.recyclerView.visibility = View.VISIBLE
                    holder.arrow.load(R.drawable.ic_arrow_drop_up)
                }
            }
        }
    }

companion object {
    fun newInstance() = StreamsFragment()
}
}