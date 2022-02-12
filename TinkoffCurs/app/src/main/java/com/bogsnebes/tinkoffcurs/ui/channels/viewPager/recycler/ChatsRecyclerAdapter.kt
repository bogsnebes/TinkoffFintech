package com.bogsnebes.tinkoffcurs.ui.channels.viewPager.recycler

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bogsnebes.tinkoffcurs.R

class ChatsRecyclerAdapter(
    private val context: Context,
    private val topicList: MutableList<TopicItem>,
    private var callbackChat: (chat: TopicItem) -> Unit
) : RecyclerView.Adapter<ChatsRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_notify_chats_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return topicList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = topicList[position].name
        holder.messages.text =
            topicList[position].countMessage.toString() + context.getString(R.string.mes)
        if (position % 2 == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.layout.setBackgroundColor(context.getColor(R.color.blue))
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.layout.setBackgroundColor(context.getColor(R.color.yellow))
            }
        }
        holder.itemView.setOnClickListener {
            callbackChat(topicList[position])
        }
    }

    fun setItems(newList: List<TopicItem>) {
        topicList.clear()
        topicList.addAll(newList)
        notifyDataSetChanged()
    }

    fun setCallbackChat(callbackChatTwo: (chat: TopicItem) -> Unit) {
        callbackChat = callbackChatTwo
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val layout: LinearLayout = view.findViewById(R.id.chatLayout)
        val name: TextView = view.findViewById(R.id.nameStreamChatTv)
        val messages: TextView = view.findViewById(R.id.messageStreamChatTv)
    }
}