package com.bogsnebes.tinkoffcurs.ui.chat.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bogsnebes.tinkoffcurs.R
import com.bogsnebes.tinkoffcurs.ui.custom.FlexBoxLayout
import com.bogsnebes.tinkoffcurs.ui.custom.message.MessageView
import com.bogsnebes.tinkoffcurs.ui.custom.message.ReceivedMessageView
import com.bogsnebes.tinkoffcurs.ui.custom.reaction.ReactionButton

class MessageAdapter(
    private val context: Context,
    private var messageList: MutableList<MessageItem>,
    private val userId: Int,
    private val callbackAddReaction: (holder: ViewHolder, messageId: Long) -> Unit,
    private val callbackReaction: (reactionButton: ReactionButton, flexBoxLayout: FlexBoxLayout, messageId: Long) -> Unit
) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

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
        holder.messageView.removeReactions()
        if (message.reactions.isNotEmpty()) {
            for (reaction in message.reactions) {
                holder.messageView.addReaction(
                    reaction.emoji,
                    reaction.countReactions,
                    reaction.selectedUser
                )
            }
        }

        holder.messageView.setMessageContent(message.message)
        if (message.userId != userId) {
            holder.messageView as ReceivedMessageView
            holder.messageView.setTitleContent(message.sender)
            if (message.profileImage == null)
                holder.messageView.setAvatar(R.drawable.ic_launcher_background)
            else
                holder.messageView.setAvatar(message.profileImage)
        }

        holder.itemView.setOnLongClickListener {
            callbackAddReaction(holder, messageList[position].id)
            true
        }
        holder.messageView.setOnAddReactionClickListener {
            callbackAddReaction(holder, messageList[position].id)
        }
        holder.messageView.setOnReactionClickListener { reactionButton, flexBoxLayout ->
            callbackReaction(reactionButton, flexBoxLayout, messageList[position].id)
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (messageList[position].userId == userId)
            MSG_TYPE_RIGHT
        else MSG_TYPE_LEFT
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val messageView: MessageView = view.findViewById(R.id.messageView)
    }

    fun setItems(newList: List<MessageItem>) {
        messageList.clear()
        messageList.addAll(newList)
        notifyDataSetChanged()
    }

    companion object {
        private const val MSG_TYPE_LEFT = 0
        private const val MSG_TYPE_RIGHT = 1
    }
}