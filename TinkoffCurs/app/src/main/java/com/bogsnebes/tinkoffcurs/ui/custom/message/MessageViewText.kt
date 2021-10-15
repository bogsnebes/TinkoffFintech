package com.bogsnebes.tinkoffcurs.ui.custom.message

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class MessageViewText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val textMessage = getChildAt(0)
        val titleMessage: View? = getChildAt(1)

        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var totalWidth = 0
        var totalHeight = 0
        var marginBottom = 0

        titleMessage?.let {
            measureChildWithMargins(it, widthMeasureSpec, 0, heightMeasureSpec, 0)
            marginBottom = (it.layoutParams as MarginLayoutParams).bottomMargin
            totalHeight = maxOf(totalHeight, it.measuredHeight)
        }

        measureChild(
            textMessage,
            widthMeasureSpec,
            heightMeasureSpec - (titleMessage?.measuredHeight ?: 0) - marginBottom
        )
        totalWidth = maxOf(textMessage.measuredWidth, titleMessage?.measuredWidth ?: 0)
        totalHeight += textMessage.measuredHeight + marginBottom

        if (totalWidth > widthSize) {
            totalWidth = widthSize
        }

        val resultWidth = resolveSize(totalWidth + paddingRight + paddingLeft, widthMeasureSpec)
        val resultHeight = resolveSize(totalHeight + paddingTop + paddingBottom, heightMeasureSpec)
        setMeasuredDimension(resultWidth, resultHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val textMessage = getChildAt(0)
        val titleMessage: View? = getChildAt(1)
        var marginBottom = 0

        titleMessage?.let {
            it.layout(
                paddingLeft,
                paddingTop,
                paddingLeft + titleMessage.measuredWidth,
                paddingTop + titleMessage.measuredHeight
            )
            marginBottom = (titleMessage.layoutParams as MarginLayoutParams).bottomMargin
        }

        textMessage.layout(
            paddingLeft,
            (titleMessage?.measuredHeight ?: paddingTop) + marginBottom,
            paddingLeft + textMessage.measuredWidth,
            (titleMessage?.measuredHeight ?: paddingTop) + marginBottom + textMessage.measuredHeight
        )
    }

    fun setMessageContent(message: String) {
        val textMessage = getChildAt(0) as TextView
        textMessage.text = message
        requestLayout()
    }

    fun setTitleContent(title: String) {
        val titleMessage: TextView? = getChildAt(1) as TextView
        titleMessage?.apply {
            this.text = title
            requestLayout()
        }
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
