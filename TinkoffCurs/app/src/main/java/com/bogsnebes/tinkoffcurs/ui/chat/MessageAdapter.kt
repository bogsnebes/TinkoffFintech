package com.bogsnebes.tinkoffcurs.ui.chat

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bogsnebes.tinkoffcurs.R
import com.bogsnebes.tinkoffcurs.data.dto.MessageDto
import com.bogsnebes.tinkoffcurs.ui.custom.message.MessageView
import com.bogsnebes.tinkoffcurs.ui.custom.message.ReceivedMessageView
import com.bogsnebes.tinkoffcurs.ui.custom.reaction.ReactionButton


class MessageAdapter(private val context: Context, private val messageList: List<MessageDto>) :
    RecyclerView.Adapter<MessageAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == MSG_TYPE_LEFT) {
            val view =
                LayoutInflater.from(context)
                    .inflate(R.layout.item_message_received_adapter, parent, false)
            ViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(context)
                    .inflate(R.layout.item_sent_message_adapter, parent, false)
            ViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messageList[position]
        val idUser = 123
        val reactions: MutableList<ReactionButton> = mutableListOf()
        for (reaction in message.reactions) {
            reactions.add(ReactionButton(context).apply {
                emoji = reaction.emoji
                countReactions = reaction.countReactions
                val padding10inDp = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 10f, resources.displayMetrics
                ).toInt()
                val padding5inDp = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 5f, resources.displayMetrics
                ).toInt()
                this.setPadding(padding10inDp, padding5inDp, padding10inDp, padding5inDp)
                this.setTextColor(Color.WHITE)
                this.setBackgroundResource(R.drawable.bg_emoji_reaction_button)
            })
        }

        holder.messageView.setMessageContent(message.message)
        holder.messageView.addReactions(reactions)
        if (message.userId == idUser) {
            holder.messageView.setMessageColorBackground(R.drawable.bg_sent_message_text)
        } else {
            holder.messageView as ReceivedMessageView
            holder.messageView.setTitleContent(message.sender)
            if (message.profileImage == null)
                holder.messageView.setAvatar(R.drawable.ic_launcher_background)
            else
                holder.messageView.setAvatar(message.profileImage)
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
        val idUser = 123
        if (messageList[position].userId == idUser) {
            return MSG_TYPE_RIGHT
        }
        return MSG_TYPE_LEFT
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val messageView: MessageView = view.findViewById(R.id.messageView)
    }

    companion object {
        private const val MSG_TYPE_LEFT = 0
        private const val MSG_TYPE_RIGHT = 1
    }
}