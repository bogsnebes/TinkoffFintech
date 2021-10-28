package com.bogsnebes.tinkoffcurs.ui.chat

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.TypedValue
import android.view.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bogsnebes.tinkoffcurs.R
import com.bogsnebes.tinkoffcurs.data.dto.MessageDto
import com.bogsnebes.tinkoffcurs.ui.custom.message.MessageView
import com.bogsnebes.tinkoffcurs.ui.custom.message.ReceivedMessageView
import com.bogsnebes.tinkoffcurs.ui.custom.reaction.ReactionButton

class MessageAdapter(
    private val context: Context,
    private var messageList: List<MessageDto>
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
        val reactions: MutableList<ReactionButton> = mutableListOf()
        if (message.reactions.isNotEmpty()) {
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
            holder.messageView.addReactions(reactions)
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
            showBottomDialog(holder)
            true
        }
        holder.messageView.setOnAddReactionClickListener {
            showBottomDialog(holder)
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

    private fun showBottomDialog(holder: ViewHolder) {
        val bottomSheetDialog = Dialog(context)
        bottomSheetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        bottomSheetDialog.setContentView(R.layout.dialog_emoji)
        bottomSheetDialog.show()
        bottomSheetDialog.window?.let { window ->
            window.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window.attributes.windowAnimations = R.style.DialogAnimation
            window.setGravity(Gravity.BOTTOM)
            val listEmoji: List<String> = context.getString(R.string.emoji).split(" ")
            val recyclerView = window.findViewById<RecyclerView>(R.id.emojiRv)
            recyclerView.layoutManager =
                GridLayoutManager(context, 7)
            recyclerView.adapter =
                DialogEmojiAdapter(context, listEmoji) {
                    holder.messageView.addReaction(it, 1)
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
                    holder.messageView.setOnAddReactionClickListener {
                        showBottomDialog(holder)
                    }
                    bottomSheetDialog.dismiss()
                }
        }
    }

    companion object {
        private const val MSG_TYPE_LEFT = 0
        private const val MSG_TYPE_RIGHT = 1
    }
}