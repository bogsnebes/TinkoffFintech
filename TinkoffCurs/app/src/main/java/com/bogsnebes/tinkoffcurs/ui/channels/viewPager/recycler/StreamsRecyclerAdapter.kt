package com.bogsnebes.tinkoffcurs.ui.channels.viewPager.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bogsnebes.tinkoffcurs.R
import com.bogsnebes.tinkoffcurs.data.dto.StreamDto

class StreamsRecyclerAdapter(
    private val context: Context,
    private val streamsList: List<StreamDto>,
    private val callback: (stream: StreamDto, holder: ViewHolder) -> Unit
) : RecyclerView.Adapter<StreamsRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.item_streams_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return streamsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = streamsList[position].category
        holder.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ChatsRecyclerAdapter(context, streamsList[position].chats) { _, _ ->
                Unit
            }
        }
        holder.itemView.setOnClickListener {
            callback(streamsList[position], holder)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.nameStreamTv)
        val recyclerView: RecyclerView = view.findViewById(R.id.streamRv)
        val arrow: ImageView = view.findViewById(R.id.arrowStreamIv)
    }
}