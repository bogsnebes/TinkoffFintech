package com.bogsnebes.tinkoffcurs.ui.chat.recycler

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bogsnebes.tinkoffcurs.R
import com.bogsnebes.tinkoffcurs.ui.custom.message.MessageView
import com.bogsnebes.tinkoffcurs.ui.custom.message.ReceivedMessageView

class MessageAdapter(
    private val context: Context,
    private var messageList: List<MessageDto>,
    private val callbackAddReaction: (holder: ViewHolder) -> Unit
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
        val idUser = 123
        holder.messageView.removeReactions()
        if (message.reactions.isNotEmpty()) {
            for (reaction in message.reactions) {
                holder.messageView.addReaction(reaction.emoji, reaction.countReactions)
            }
        }

        holder.messageView.setMessageContent(message.message)
        if (message.userId != idUser) {
            holder.messageView as ReceivedMessageView
            holder.messageView.setTitleContent(message.sender)
            if (message.profileImage == null)
                holder.messageView.setAvatar(R.drawable.ic_launcher_background)
            else
                holder.messageView.setAvatar(message.profileImage)
        }

        holder.itemView.setOnLongClickListener {
            callbackAddReaction(holder)
            true
        }
        holder.messageView.setOnAddReactionClickListener {
            callbackAddReaction(holder)
        }
        holder.messageView.setOnReactionClickListener { reactionButton, flexBoxLayout ->
            reactionButton.isSelected = !reactionButton.isSelected
            if (!reactionButton.isSelected) {
                reactionButton.setTextColor(Color.WHITE)
                reactionButton.countReactions++
            } else {
                reactionButton.setTextColor(Color.GRAY)
                reactionButton.countReactions--
            }
            if (reactionButton.countReactions <= 0) {
                flexBoxLayout.removeView(reactionButton)
            }
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
        val idUser = 123
        return if (messageList[position].userId == idUser)
            MSG_TYPE_RIGHT
        else MSG_TYPE_LEFT
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val messageView: MessageView = view.findViewById(R.id.messageView)
    }

    companion object {
        private const val MSG_TYPE_LEFT = 0
        private const val MSG_TYPE_RIGHT = 1
    }
}