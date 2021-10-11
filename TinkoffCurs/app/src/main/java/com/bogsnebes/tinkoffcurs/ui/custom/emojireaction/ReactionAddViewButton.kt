package com.bogsnebes.tinkoffcurs.ui.custom.emojireaction

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.bogsnebes.tinkoffcurs.R

internal class ReactionAddViewButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var text = resources.getString(R.string.add_view_button)

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = resources.getDimension(R.dimen.simple_text)
        textAlign = Paint.Align.CENTER
        color = Color.WHITE
    }

    private val viewRect = Rect()
    private val textCoordinate = PointF()

    private val tempFontMetrics = Paint.FontMetrics()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        textPaint.getTextBounds(
            "\uD83D\uDE0E 2",
            0,
            "\uD83D\uDE0E 2".length,
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

    override fun onDraw(canvas: Canvas) {
        canvas.drawText(text, textCoordinate.x, textCoordinate.y, textPaint)
    }
}