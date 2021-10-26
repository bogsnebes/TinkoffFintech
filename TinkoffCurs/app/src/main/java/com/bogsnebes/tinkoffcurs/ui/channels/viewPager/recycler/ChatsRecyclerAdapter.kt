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
import com.bogsnebes.tinkoffcurs.data.dto.ChatDto
import com.bogsnebes.tinkoffcurs.data.dto.ProfileDto

class ChatsRecyclerAdapter(
    private val context: Context,
    private val chatList: List<ChatDto>,
    private val callback: (profile: ProfileDto) -> Unit
) : RecyclerView.Adapter<ChatsRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_streams_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = chatList[position].name
        holder.messages.text =
            chatList[position].countMessages.toString() + context.getString(R.string.mes)
        if (position % 2 == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.layout.setBackgroundColor(context.getColor(R.color.yellow))
            }
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.layout.setBackgroundColor(context.getColor(R.color.blue))
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val layout: LinearLayout = view.findViewById(R.id.chatLayout)
        val name: TextView = view.findViewById(R.id.nameStreamTv)
        val messages: TextView = view.findViewById(R.id.messageStreamChatTv)
    }
}