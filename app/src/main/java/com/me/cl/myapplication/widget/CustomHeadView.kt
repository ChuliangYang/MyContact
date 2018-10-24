package com.me.cl.myapplication.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.widget.ImageView

/**
 * Created by CL on 10/19/18.
 */
class CustomHeadView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {

    var mPaintBackground: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var mPaintText: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var mRect: Rect = Rect()

    var character: Char? = null

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        character?.run {
            canvas.drawCircle((width / 2).toFloat(), (width / 2).toFloat(), (width / 2).toFloat(), mPaintBackground)
            mPaintText.apply {
                color = Color.WHITE
                textSize = (width / 2).toFloat()
                strokeWidth = 3F
                getTextBounds(character.toString(), 0, 1, mRect)
            }

            val fontMetrics = mPaintText.fontMetricsInt
            val baseline = (measuredHeight - fontMetrics.bottom - fontMetrics.top) / 2
            mPaintText.textAlign = Paint.Align.CENTER
            canvas.drawText(character.toString().toUpperCase(), (width / 2).toFloat(), baseline.toFloat(), mPaintText)
        }
    }

    fun setCharacter(character: Char) {
        this.character = character
        invalidate()
    }

}
