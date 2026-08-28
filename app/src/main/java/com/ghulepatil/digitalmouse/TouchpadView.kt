package com.ghulepatil.digitalmouse

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import kotlin.math.roundToInt

class TouchpadView(context: Context) : View(context) {
    interface Listener {
        fun move(dx: Int, dy: Int)
        fun scroll(amount: Int)
    }

    var listener: Listener? = null
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var lastX = 0f
    private var lastY = 0f
    private var multi = false

    override fun onDraw(canvas: Canvas) {
        paint.color = Color.rgb(27, 30, 37)
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
        paint.color = Color.rgb(112, 118, 130)
        paint.textAlign = Paint.Align.CENTER
        paint.textSize = 18f
        canvas.drawText("TOUCHPAD", width / 2f, height / 2f, paint)
        paint.textSize = 13f
        canvas.drawText("1 finger: move   •   2 fingers: scroll", width / 2f, height / 2f + 28f, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.x
                lastY = event.y
                multi = false
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                if (event.pointerCount > 1) multi = true
                lastY = event.getY(0)
            }
            MotionEvent.ACTION_MOVE -> {
                if (event.pointerCount > 1 || multi) {
                    val dy = (event.getY(0) - lastY).roundToInt()
                    if (dy != 0) listener?.scroll(-dy / 3)
                    lastY = event.getY(0)
                } else {
                    val dx = (event.x - lastX).roundToInt()
                    val dy = (event.y - lastY).roundToInt()
                    if (dx != 0 || dy != 0) listener?.move(dx * 2, dy * 2)
                    lastX = event.x
                    lastY = event.y
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> multi = false
        }
        return true
    }
}
