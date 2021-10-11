package com.bogsnebes.tinkoffcurs.ui.custom.emojireaction

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children
import com.bogsnebes.tinkoffcurs.R
import kotlin.math.ceil

class ReactionFlexBoxLayout @JvmOverloads constructor(
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
            R.styleable.ReactionFlexBoxLayout,
            defStyleAttr,
            defStyleRes
        )
        spaceBetweenHorizontal =
            typedArray.getDimension(
                R.styleable.ReactionFlexBoxLayout_erfb_space_between_horizontal,
                0F
            )
        spaceBetweenVertical =
            typedArray.getDimension(
                R.styleable.ReactionFlexBoxLayout_erfb_space_between_vertical,
                0F
            )
        typedArray.recycle()
    }

    fun setOnAddReactionClickListener(addReactionClickListener: () -> Unit) {
        val addReactionButton =
            this.children.firstOrNull { child -> child is ReactionAddViewButton }
        addReactionButton?.setOnClickListener {
            addReactionClickListener()
        }
    }

    fun setOnReactionClickListener(reactionClickListener: () -> Unit) {
        children.filter { it is ReactionButton }.forEach {
            it.setOnClickListener {
                reactionClickListener()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var totalWidth = 0
        var totalHeight = 0
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            totalHeight = maxOf(totalHeight, child.measuredHeight)
            totalWidth += child.measuredWidth + spaceBetweenHorizontal.toInt()
        }
        val maxChildHeight: Int = totalHeight
        var countStrings: Int = ceil(totalWidth.toFloat() / widthSize.toFloat()).toInt()
        while (countStrings > 1) {
            countStrings--
            totalHeight += maxChildHeight + spaceBetweenVertical.toInt()
        }
        val resultWidth = resolveSize(totalWidth, widthMeasureSpec)
        val resultHeight = resolveSize(totalHeight, heightMeasureSpec)
        setMeasuredDimension(resultWidth, resultHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var currentRight = 0
        var currentTop = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (currentRight + child.measuredWidth > measuredWidth) {
                currentTop += child.measuredHeight + spaceBetweenVertical.toInt()
                currentRight = 0
            }
            child.layout(
                currentRight,
                currentTop,
                currentRight + child.measuredWidth,
                currentTop + child.measuredHeight
            )
            currentRight = child.right + spaceBetweenHorizontal.toInt()
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