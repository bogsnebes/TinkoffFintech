package com.bogsnebes.tinkoffcurs.ui.custom.message

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import com.bogsnebes.tinkoffcurs.R
import com.bogsnebes.tinkoffcurs.ui.custom.emojireaction.ReactionFlexBoxLayout

class MessageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {
    init {
        inflate(context, R.layout.item_message_view, this)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val imageView = getChildAt(0)
        val messageTextView = getChildAt(1)
        val reactionFlexBoxLayout = getChildAt(2)

        var totalWidth = 0
        var totalHeight = 0

        measureChildWithMargins(imageView, widthMeasureSpec, 0, heightMeasureSpec, 0)

        val marginRight = (imageView.layoutParams as MarginLayoutParams).rightMargin
        val marginBottom = (messageTextView.layoutParams as MarginLayoutParams).bottomMargin

        measureChild(
            reactionFlexBoxLayout,
            widthMeasureSpec - imageView.measuredWidth - marginRight - paddingLeft,
            heightMeasureSpec - messageTextView.measuredHeight - marginBottom,
        )
        totalWidth =
            reactionFlexBoxLayout.measuredWidth
        totalHeight += reactionFlexBoxLayout.measuredHeight

        measureChild(
            messageTextView,
            widthMeasureSpec - imageView.measuredWidth - marginRight - paddingLeft,
            heightMeasureSpec - reactionFlexBoxLayout.measuredHeight,
        )
        totalWidth += maxOf(
            totalWidth,
            messageTextView.measuredWidth
        )
        totalHeight += messageTextView.measuredHeight + marginBottom

        totalWidth += imageView.measuredWidth + paddingLeft + marginRight
        totalHeight = maxOf(totalHeight, imageView.measuredHeight)

        val resultWidth = resolveSize(totalWidth + paddingRight + paddingLeft, widthMeasureSpec)
        val resultHeight = resolveSize(totalHeight + paddingTop + paddingBottom, heightMeasureSpec)
        setMeasuredDimension(resultWidth, resultHeight)
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val imageView = getChildAt(0)
        val messageTextView = getChildAt(1)
        val reactionFlexBoxLayout = getChildAt(2)

        imageView.layout(
            paddingLeft,
            paddingTop,
            paddingLeft + imageView.measuredWidth,
            paddingTop + imageView.measuredHeight
        )

        val marginRight = (imageView.layoutParams as MarginLayoutParams).rightMargin
        val marginBottom = (messageTextView.layoutParams as MarginLayoutParams).bottomMargin

        messageTextView.layout(
            paddingLeft + imageView.measuredWidth + marginRight,
            paddingTop,
            paddingLeft + imageView.measuredWidth + marginRight + messageTextView.measuredWidth,
            paddingTop + messageTextView.measuredHeight
        )

        reactionFlexBoxLayout.layout(
            paddingLeft + imageView.measuredWidth + marginRight,
            paddingTop + messageTextView.measuredHeight + marginBottom,
            paddingLeft + imageView.measuredWidth + marginRight + reactionFlexBoxLayout.measuredWidth,
            paddingTop + messageTextView.measuredHeight + reactionFlexBoxLayout.measuredHeight + marginBottom
        )
    }

    fun setOnAddReactionClickListener(addReactionClickListener: () -> Unit) {
        val emojiReactionFlexBoxLayout = getChildAt(2) as ReactionFlexBoxLayout
        emojiReactionFlexBoxLayout.setOnAddReactionClickListener {
            addReactionClickListener()
        }
    }

    fun setOnReactionClickListener(reactionClickListener: () -> Unit) {
        val emojiReactionFlexBoxLayout = getChildAt(2) as ReactionFlexBoxLayout
        emojiReactionFlexBoxLayout.setOnReactionClickListener { reactionClickListener() }
    }

    fun setMessageContent(title: String, message: String) {
        val messageTextView = getChildAt(1) as MessageViewText
        messageTextView.setMessageContent(title, message)
        requestLayout()
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun checkLayoutParams(p: LayoutParams): Boolean {
        return p is MarginLayoutParams
    }

    override fun generateLayoutParams(p: LayoutParams): LayoutParams {
        return MarginLayoutParams(p)
    }
}