package com.bogsnebes.tinkoffcurs.ui.custom.EmojiReaction

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.bogsnebes.tinkoffcurs.R

class EmojiReactionButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var countReactions = ""
        set(value) {
            field = value
            requestLayout()
        }

    private var emoji = ""
        set(value) {
            field = value
            requestLayout()
        }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = resources.getDimension(R.dimen.simple_text)
        textAlign = Paint.Align.CENTER
        color = Color.RED
    }
    private val viewRect = Rect()
    private val textCoordinate = PointF()

    private val tempFontMetrics = Paint.FontMetrics()

    init {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.EmojiReactionButton,
            defStyleAttr,
            defStyleRes
        )
        countReactions =
            typedArray.getString(R.styleable.EmojiReactionButton_erb_count_reactions).orEmpty()
        emoji =
            typedArray.getString(R.styleable.EmojiReactionButton_erb_emoji).orEmpty()
        textPaint.color =
            typedArray.getColor(R.styleable.EmojiReactionButton_erb_color, Color.WHITE)

        textPaint.textSize = typedArray.getDimension(
            R.styleable.EmojiReactionButton_erb_text_size,
            resources.getDimension(R.dimen.simple_text)
        )
        typedArray.recycle()
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
        isSelected = !isSelected
        if (!isSelected) {
            setTextColor(Color.WHITE)
        } else {
            setTextColor(Color.GRAY)
        }
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
        val drawableState = super.onCreateDrawableState(extraSpace + SUPPORTED_DRAWABLE_STATE.size)
        if (isSelected) {
            mergeDrawableStates(drawableState, SUPPORTED_DRAWABLE_STATE)
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
        private val SUPPORTED_DRAWABLE_STATE = intArrayOf(android.R.attr.state_selected)
    }

}