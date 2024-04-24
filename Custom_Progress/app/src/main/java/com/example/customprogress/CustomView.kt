package com.example.customprogress

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class CustomView(context: Context, attrs: AttributeSet):View(context, attrs){
    private var progress = 0f
    private val maxProgress = 1f

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLUE
        style = Paint.Style.STROKE
        strokeWidth = 60f
    }

    private val ovalBounds = RectF()

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textSize = 60f

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f
        val radius = (Math.min(centerX, centerY) - progressPaint.strokeWidth / 2f).coerceAtLeast(0f)

        val sweepAngle = 360f * (progress / maxProgress)

        ovalBounds.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius)

        canvas.drawArc(ovalBounds, -90f, sweepAngle, false, progressPaint)

        val text = "${(progress * 100).toInt()}%"
        val textWidth = textPaint.measureText(text)
        val textX = centerX - textWidth / 2
        val textY = centerY + textPaint.textSize / 2
        canvas.drawText(text, textX, textY, textPaint)
    }

    fun setProgress(progress: Float) {
        this.progress = progress.coerceIn(0f, maxProgress)
        invalidate()
    }
}
