package com.bogsnebes.tinkoffcurs.ui.custom.message

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.bogsnebes.tinkoffcurs.R
import com.bogsnebes.tinkoffcurs.ui.custom.FlexBoxLayout

class ReceivedMessageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : MessageView(context, attrs, defStyleAttr, defStyleRes) {
    private val imageView: ImageView
    override val messageTextView: MessageViewText
    override val flexBoxLayout: FlexBoxLayout

    init {
        inflate(context, R.layout.item_message_received_view, this)
        imageView = findViewById(R.id.avatarIv)
        messageTextView = findViewById(R.id.messageMvt)
        flexBoxLayout = findViewById(R.id.rfbl)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var totalWidth = 0
        var totalHeight = 0

        measureChildWithMargins(imageView, widthMeasureSpec, 0, heightMeasureSpec, 0)

        val marginRight = (imageView.layoutParams as MarginLayoutParams).rightMargin
        val marginBottom = (messageTextView.layoutParams as MarginLayoutParams).bottomMargin

        measureChild(
            flexBoxLayout,
            widthMeasureSpec - imageView.measuredWidth - marginRight - paddingLeft,
            heightMeasureSpec - messageTextView.measuredHeight - marginBottom,
        )
        totalWidth =
            flexBoxLayout.measuredWidth
        totalHeight += flexBoxLayout.measuredHeight

        measureChild(
            messageTextView,
            widthMeasureSpec - imageView.measuredWidth - marginRight - paddingLeft,
            heightMeasureSpec - flexBoxLayout.measuredHeight,
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

        flexBoxLayout.layout(
            paddingLeft + imageView.measuredWidth + marginRight,
            paddingTop + messageTextView.measuredHeight + marginBottom,
            paddingLeft + imageView.measuredWidth + marginRight + flexBoxLayout.measuredWidth,
            paddingTop + messageTextView.measuredHeight + flexBoxLayout.measuredHeight + marginBottom
        )
    }

    fun setTitleContent(title: String) {
        messageTextView.setTitleContent(title)
        requestLayout()
    }

    fun setAvatar(drawable: Int) {
        imageView.load(drawable) {
            transformations(
                RoundedCornersTransformation(CORNERS_RADIUS)
            )
        }
        requestLayout()
    }

    fun setAvatar(uri: String) {
        imageView.load(uri) {
            transformations(
                RoundedCornersTransformation(CORNERS_RADIUS)
            )
        }
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

    companion object {
        private const val CORNERS_RADIUS = 46f
    }
}