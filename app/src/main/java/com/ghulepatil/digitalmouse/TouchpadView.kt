package com.ghulepatil.digitalmouse

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class TouchpadView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    interface Listener {
        fun move(dx: Int, dy: Int)
        fun scroll(amount: Int)
    }

    var listener: Listener? = null

    private var lastX = 0f
    private var lastY = 0f
    private var downTime = 0L
    private var moved = false

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.actionMasked) {

            MotionEvent.ACTION_DOWN -> {
                lastX = event.x
                lastY = event.y
                downTime = System.currentTimeMillis()
                moved = false
                return true
            }

            MotionEvent.ACTION_MOVE -> {

                val dx = (event.x - lastX).toInt()
                val dy = (event.y - lastY).toInt()

                if (dx != 0 || dy != 0) {
                    moved = true
                    listener?.move(dx, dy)
                }

                lastX = event.x
                lastY = event.y

                return true
            }

            MotionEvent.ACTION_UP -> {
                performClick()
                return true
            }

            MotionEvent.ACTION_CANCEL -> {
                return true
            }
        }

        return true
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }
}
