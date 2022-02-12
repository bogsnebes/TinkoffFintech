package com.bogsnebes.tinkoffcurs.ui.custom.message

import android.content.Context
import android.util.AttributeSet
import com.bogsnebes.tinkoffcurs.R
import com.bogsnebes.tinkoffcurs.ui.custom.FlexBoxLayout

class SentMessageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : MessageView(context, attrs, defStyleAttr, defStyleRes) {
    override val messageTextView: MessageViewText
    override val flexBoxLayout: FlexBoxLayout

    init {
        inflate(context, R.layout.item_sent_message_view, this)
        messageTextView = findViewById(R.id.messageMvt)
        flexBoxLayout = findViewById(R.id.rfbl)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var totalWidth = 0
        var totalHeight = 0

        val marginBottom = (messageTextView.layoutParams as MarginLayoutParams).bottomMargin

        measureChild(
            flexBoxLayout,
            widthMeasureSpec,
            heightMeasureSpec - messageTextView.measuredHeight - marginBottom,
        )
        totalWidth =
            flexBoxLayout.measuredWidth + paddingLeft
        totalHeight += flexBoxLayout.measuredHeight

        measureChild(
            messageTextView,
            widthMeasureSpec - paddingLeft,
            heightMeasureSpec - flexBoxLayout.measuredHeight,
        )
        totalWidth += maxOf(
            totalWidth,
            messageTextView.measuredWidth
        )
        totalHeight += messageTextView.measuredHeight + marginBottom

        val resultWidth = resolveSize(totalWidth + paddingRight + paddingLeft, widthMeasureSpec)
        val resultHeight = resolveSize(totalHeight + paddingTop + paddingBottom, heightMeasureSpec)
        setMeasuredDimension(resultWidth, resultHeight)
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val marginBottom = (messageTextView.layoutParams as MarginLayoutParams).bottomMargin

        messageTextView.layout(
            measuredWidth - messageTextView.measuredWidth - paddingRight,
            paddingTop,
            measuredWidth + paddingRight,
            paddingTop + messageTextView.measuredHeight
        )

        flexBoxLayout.layout(
            measuredWidth - flexBoxLayout.measuredWidth - paddingRight,
            paddingTop + messageTextView.measuredHeight + marginBottom,
            measuredWidth + paddingRight,
            paddingTop + messageTextView.measuredHeight + flexBoxLayout.measuredHeight + marginBottom
        )
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