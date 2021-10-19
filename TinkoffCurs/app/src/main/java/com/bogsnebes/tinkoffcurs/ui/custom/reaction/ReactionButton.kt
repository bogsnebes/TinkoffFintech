package com.bogsnebes.tinkoffcurs.ui.custom.reaction

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.bogsnebes.tinkoffcurs.R

class ReactionButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr) {
    var countReactions = 0
        set(value) {
            field = value
            requestLayout()
        }

    var emoji = ""
        set(value) {
            field = value
            requestLayout()
        }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = resources.getDimension(R.dimen.simple_text)
        textAlign = Paint.Align.CENTER
    }
    private val viewRect = Rect()
    private val textCoordinate = PointF()

    private val tempFontMetrics = Paint.FontMetrics()

    init {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.ReactionButton,
            defStyleAttr,
            defStyleRes
        )
        countReactions =
            typedArray.getInteger(R.styleable.ReactionButton_erb_count_reactions, 1)
        emoji =
            typedArray.getString(R.styleable.ReactionButton_erb_emoji).orEmpty()

        textPaint.textSize = typedArray.getDimension(
            R.styleable.ReactionButton_erb_text_size,
            resources.getDimension(R.dimen.simple_text)
        )
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        textPaint.getTextBounds(
            "$countReactions $emoji",
            0,
            "$countReactions $emoji".length,
            viewRect
        )

        val totalHeight = viewRect.height() + paddingTop + paddingBottom
        val totalWidth = viewRect.width() + paddingRight + paddingLeft

        val resultWidth = resolveSize(totalWidth, widthMeasureSpec)
        val resultHeight = resolveSize(totalHeight, heightMeasureSpec)

        setMeasuredDimension(resultWidth, resultHeight)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        textPaint.getFontMetrics(tempFontMetrics)

        textCoordinate.x = w / 2f
        textCoordinate.y = h / 2f + viewRect.height() / 2 - tempFontMetrics.descent
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + EXTRA_SUPPORTED_DRAWABLE_STATE.size)
        if (isSelected) {
            mergeDrawableStates(drawableState, EXTRA_SUPPORTED_DRAWABLE_STATE)
        }
        return drawableState
    }

    fun setTextColor(color: Int) {
        textPaint.color = color
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawText("$emoji $countReactions", textCoordinate.x, textCoordinate.y, textPaint)
    }

    companion object {
        private val EXTRA_SUPPORTED_DRAWABLE_STATE = intArrayOf(android.R.attr.state_selected)
    }

}