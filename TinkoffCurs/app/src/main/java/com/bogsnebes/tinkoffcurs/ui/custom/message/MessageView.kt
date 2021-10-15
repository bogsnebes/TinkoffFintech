package com.bogsnebes.tinkoffcurs.ui.custom.message

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
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
    abstract val flexBoxLayout: FlexBoxLayout
    abstract val messageTextView: MessageViewText

    fun setOnAddReactionClickListener(addReactionClickListener: () -> ReactionButton) {
        val addReactionButton =
            flexBoxLayout.children.firstOrNull { child -> child is ReactionAddViewButton }
        addReactionButton?.setOnClickListener {
            flexBoxLayout.removeView(addReactionButton)
            flexBoxLayout.addView(addReactionClickListener())
            flexBoxLayout.addView(addReactionButton)
            requestLayout()
        }
    }

    fun setOnReactionClickListener(reactionClickListener: (view: View) -> Unit) {
        flexBoxLayout.children.filter { it is ReactionButton }.forEach { it ->
            it.setOnClickListener {
                it as ReactionButton
                it.isSelected = !it.isSelected
                if (!it.isSelected) {
                    it.setTextColor(Color.WHITE)
                } else {
                    it.setTextColor(Color.GRAY)
                }
                reactionClickListener(it)
            }
        }
    }

    fun setMessageContent(message: String) {
        messageTextView.setMessageContent(message)
        requestLayout()
    }

    fun addReactions(reactions: List<ReactionButton>) {
        flexBoxLayout.addChildren(reactions)
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
        requestLayout()
    }

    fun setMessageColorBackground(background: Int) {
        messageTextView.setBackgroundResource(background)
        invalidate()
    }
}