package com.bogsnebes.tinkoffcurs.ui.custom.EmojiReaction

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import com.bogsnebes.tinkoffcurs.R

class EmojiReactionFlexBoxLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {
    private var spaceBetweenHorizontal = 0f
        set(value) {
            field = value
            requestLayout()
        }
    private var spaceBetweenVertical = 0f
        set(value) {
            field = value
            requestLayout()
        }

    init {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.EmojiReactionFlexBoxLayout,
            defStyleAttr,
            defStyleRes
        )
        spaceBetweenHorizontal =
            typedArray.getDimension(
                R.styleable.EmojiReactionFlexBoxLayout_erfb_space_between_horizontal,
                0F
            )
        spaceBetweenVertical =
            typedArray.getDimension(
                R.styleable.EmojiReactionFlexBoxLayout_erfb_space_between_vertical,
                0F
            )
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var totalWidth = 0
        var totalHeight = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, totalWidth)
            totalHeight = maxOf(totalHeight, child.measuredHeight)
            totalWidth += child.measuredWidth + spaceBetweenHorizontal.toInt()
        }
        val maxChildHeight: Int = totalHeight
        while (totalWidth > MeasureSpec.getSize(widthMeasureSpec)) {
            totalHeight += maxChildHeight + spaceBetweenVertical.toInt()
            totalWidth -= MeasureSpec.getSize(widthMeasureSpec) - spaceBetweenHorizontal.toInt() * 2
        }
        val resultWidth = resolveSize(totalWidth, widthMeasureSpec)
        val resultHeight = resolveSize(totalHeight, heightMeasureSpec)
        setMeasuredDimension(resultWidth, resultHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var currentRight = 0 - spaceBetweenHorizontal.toInt()
        var currentTop = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (spaceBetweenHorizontal + currentRight + child.measuredWidth > measuredWidth) {
                currentTop += child.measuredHeight + spaceBetweenVertical.toInt()
                currentRight = 0 - spaceBetweenHorizontal.toInt()
            }
            child.layout(
                spaceBetweenHorizontal.toInt() + currentRight,
                currentTop,
                currentRight + child.measuredWidth,
                currentTop + child.measuredHeight
            )
            currentRight = child.right
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