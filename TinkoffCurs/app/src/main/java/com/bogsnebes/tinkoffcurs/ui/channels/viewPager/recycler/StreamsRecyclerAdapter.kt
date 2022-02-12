package com.bogsnebes.tinkoffcurs.ui.channels.viewPager.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bogsnebes.tinkoffcurs.R

class StreamsRecyclerAdapter(
    private val context: Context,
    private val streamsList: MutableList<StreamItem>,
    private val callbackStream: (stream: StreamItem, holder: ViewHolder) -> Unit,
    private val callbackChat: (chat: TopicItem) -> Unit
) : RecyclerView.Adapter<StreamsRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.item_streams_adapter, parent, false)
        return ViewHolder(context, view) {
            callbackChat(it)
        }
    }

    override fun getItemCount(): Int {
        return streamsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = streamsList[position].name
        (holder.recyclerView.adapter as ChatsRecyclerAdapter).setItems(streamsList[position].topics)
        (holder.recyclerView.adapter as ChatsRecyclerAdapter).setCallbackChat {
            it.category = streamsList[position].name
            callbackChat(it)
        }
        holder.itemView.setOnClickListener {
            callbackStream(streamsList[position], holder)
        }
    }

    fun setItems(newList: List<StreamItem>) {
        streamsList.clear()
        streamsList.addAll(newList)
        notifyDataSetChanged()
    }

    class ViewHolder(context: Context, view: View, callbackChat: (chat: TopicItem) -> Unit) :
        RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.nameStreamTv)
        val recyclerView: RecyclerView = view.findViewById(R.id.streamRv)
        val arrow: ImageView = view.findViewById(R.id.arrowStreamIv)

        init {
            recyclerView.adapter = ChatsRecyclerAdapter(context, mutableListOf()) {
                callbackChat(it)
            }
        }
    }
}