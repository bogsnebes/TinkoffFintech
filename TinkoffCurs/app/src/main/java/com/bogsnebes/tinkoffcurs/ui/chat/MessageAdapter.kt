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
) :
    RecyclerView.Adapter<MessageAdapter.ViewHolder>() {
    private var currentViewType: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == MSG_TYPE_LEFT) {
            currentViewType = MSG_TYPE_LEFT
            val view =
                LayoutInflater.from(context)
                    .inflate(R.layout.item_message_received_adapter, parent, false)
            ViewHolder(view)
        } else {
            currentViewType = MSG_TYPE_RIGHT
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
        holder.messageView.setOnReactionClickListener { }
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

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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
            val listEmoji: List<String> = EMOJI.split(" ")
            val recyclerView = window.findViewById<RecyclerView>(R.id.emojiRv)
            recyclerView.layoutManager =
                GridLayoutManager(context, 7)
            recyclerView.adapter =
                DialogEmojiAdapter(context, listEmoji) {
                    holder.messageView.addReaction(it, 1)
                    holder.messageView.setOnReactionClickListener {}
                    holder.messageView.setOnAddReactionClickListener {
                        showBottomDialog(holder)
                    }
                    bottomSheetDialog.dismiss()
                }
        }
    }

/*
Извиняюсь, за закоментированный код, но DiffUtil как-то странно себя ведет.
Простое обновление адаптера работает почему-то быстрее

fun setData(newMessageList: List<MessageDto>) {
        val diffUtil = MessageDiffUtil(messageList, newMessageList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        messageList = newMessageList
        diffResult.dispatchUpdatesTo(this)
    }*/

    companion object {
        private const val MSG_TYPE_LEFT = 0
        private const val MSG_TYPE_RIGHT = 1
        private const val EMOJI =
            "\uD83D\uDE01 \uD83D\uDE02 \uD83D\uDE03 \uD83D\uDE06 \uD83D\uDE07 \uD83D\uDE08 \uD83D\uDE09 \uD83D\uDE0A \uD83D\uDE0B \uD83D\uDE0C \uD83D\uDE0D \uD83D\uDE0E \uD83D\uDE0F \uD83D\uDE10 \uD83D\uDE12 \uD83D\uDEA4 \uD83D\uDE13 \uD83D\uDE14 \uD83D\uDE16 \uD83D\uDE18 \uD83D\uDE1A \uD83D\uDE1C \uD83D\uDE1D \uD83D\uDE1E \uD83D\uDE20 \uD83D\uDE21 \uD83D\uDE22 \uD83D\uDE23 \uD83D\uDE24 \uD83D\uDE25 \uD83D\uDE28 \uD83D\uDE29 \uD83D\uDE2A \uD83D\uDE2B \uD83D\uDE2D \uD83D\uDE30 \uD83C\uDF0F \uD83C\uDF40 \uD83D\uDE31 \uD83D\uDE32 \uD83D\uDE33 \uD83D\uDE35 \uD83D\uDE36 \uD83D\uDE37 \uD83D\uDE38 \uD83D\uDE39 \uD83D\uDE3A \uD83D\uDE3B \uD83D\uDE3C \uD83D\uDE3D \uD83D\uDE3E \uD83D\uDE3F \uD83D\uDE40 \uD83D\uDE45 \uD83D\uDE46 \uD83D\uDE47 \uD83D\uDE48 \uD83D\uDE49 \uD83D\uDE4A \uD83D\uDE4B \uD83D\uDE4C \uD83D\uDE4D \uD83D\uDE4E ✋ ✋ \uD83D\uDC32 \uD83D\uDC40 \uD83D\uDC1D \uD83D\uDCA2 ☘ ✌"
    }
}