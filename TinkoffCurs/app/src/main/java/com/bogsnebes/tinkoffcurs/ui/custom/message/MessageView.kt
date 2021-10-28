package com.bogsnebes.tinkoffcurs.ui.custom.message

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewGroup
import androidx.core.view.children
import com.bogsnebes.tinkoffcurs.R
import com.bogsnebes.tinkoffcurs.ui.custom.FlexBoxLayout
import com.bogsnebes.tinkoffcurs.ui.custom.reaction.ReactionAddViewButton
import com.bogsnebes.tinkoffcurs.ui.custom.reaction.ReactionButton

abstract class MessageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {
    protected abstract val flexBoxLayout: FlexBoxLayout
    protected abstract val messageTextView: MessageViewText

    fun setOnAddReactionClickListener(addReactionClickListener: (addReactionButton: ReactionAddViewButton) -> Unit) {
        val addReactionButton =
            flexBoxLayout.children.firstOrNull { child -> child is ReactionAddViewButton }
        addReactionButton?.setOnClickListener {
            addReactionClickListener(it as ReactionAddViewButton)
        }
    }

    fun setOnReactionClickListener(reactionClickListener: (reactionButton: ReactionButton, flexbox: FlexBoxLayout) -> Unit) {
        flexBoxLayout.children.filter { it is ReactionButton }.forEach { it ->
            it.setOnClickListener {
                reactionClickListener(it as ReactionButton, flexBoxLayout)
            }
        }
    }

    fun setMessageContent(message: String) {
        messageTextView.setMessageContent(message)
        requestLayout()
    }

    fun removeReactions() {
        flexBoxLayout.removeAllViews()
    }

    fun addReaction(
        emojiText: String,
        count: Int
    ) {
        val addReactionButton =
            flexBoxLayout.children.firstOrNull { child -> child is ReactionAddViewButton }
        flexBoxLayout.removeView(addReactionButton)
        flexBoxLayout.addView(ReactionButton(context).apply {
            emoji = emojiText
            countReactions = count
            isSelected = false
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
        if (addReactionButton != null)
            flexBoxLayout.addView(addReactionButton)
        else {
            flexBoxLayout.addView(ReactionAddViewButton(context).apply {
                val padding10inDp = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 10f, resources.displayMetrics
                ).toInt()
                val padding5inDp = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 5f, resources.displayMetrics
                ).toInt()
                this.setPadding(padding10inDp, padding5inDp, padding10inDp, padding5inDp)
                this.setBackgroundResource(R.drawable.bg_emoji_reaction_button)
            })
        }
        requestLayout()
    }
}