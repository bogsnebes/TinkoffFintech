package com.bogsnebes.tinkoffcurs.ui.custom.message

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.TextView

class MessageViewText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val titleMessage = getChildAt(0)
        val textMessage = getChildAt(1)

        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var totalWidth = 0
        var totalHeight = 0

        measureChildWithMargins(titleMessage, widthMeasureSpec, 0, heightMeasureSpec, 0)

        val marginBottom = (titleMessage.layoutParams as MarginLayoutParams).bottomMargin
        totalHeight = maxOf(totalHeight, titleMessage.measuredHeight)

        measureChild(
            textMessage,
            widthMeasureSpec,
            heightMeasureSpec - titleMessage.measuredHeight - marginBottom
        )
        totalWidth = maxOf(textMessage.measuredWidth, titleMessage.measuredWidth)
        totalHeight += textMessage.measuredHeight + marginBottom

        if (totalWidth > widthSize) {
            totalWidth = widthSize
        }

        val resultWidth = resolveSize(totalWidth + paddingRight + paddingLeft, widthMeasureSpec)
        val resultHeight = resolveSize(totalHeight + paddingTop + paddingBottom, heightMeasureSpec)
        setMeasuredDimension(resultWidth, resultHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val titleMessage = getChildAt(0)
        val textMessage = getChildAt(1)

        titleMessage.layout(
            paddingLeft,
            paddingTop,
            paddingLeft + titleMessage.measuredWidth,
            paddingTop + titleMessage.measuredHeight
        )

        val marginBottom = (titleMessage.layoutParams as MarginLayoutParams).bottomMargin

        textMessage.layout(
            paddingLeft,
            titleMessage.measuredHeight + marginBottom,
            paddingLeft + textMessage.measuredWidth,
            titleMessage.measuredHeight + marginBottom + textMessage.measuredHeight
        )
    }

    fun setMessageContent(title: String, message: String) {
        val titleMessage = getChildAt(0) as TextView
        val textMessage = getChildAt(1) as TextView

        titleMessage.text = title
        textMessage.text = message
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
