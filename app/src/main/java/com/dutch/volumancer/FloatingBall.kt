package com.dutch.volumancer

import android.content.Context
import android.graphics.PixelFormat
import android.media.AudioManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import com.dutch.volumancer.VolumancerApplication.Companion.LOG_TAG

class FloatingBall(private val context: Context) {
    private val TAG = "$LOG_TAG FloatingBall"

    private val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private var view: View? = null


    fun showQuickBall() {
        if (view != null) return
        Log.i(TAG, "showQuickBall")

        val layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        layoutParams.gravity = Gravity.TOP or Gravity.START
        layoutParams.x = 10
        layoutParams.y = 10

        view = LayoutInflater.from(context).inflate(R.layout.quick_ball, null)
        if (view == null) {
            Log.e(TAG, "Inflated view is null!")
            return
        }
        val ball = view!!.findViewById<View>(R.id.ball)

        var initialX = 0
        var initialY = 0
        var initialTouchX = 0f
        var initialTouchY = 0f
        var isClick = true

        ball.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialX = layoutParams.x
                    initialY = layoutParams.y
                    initialTouchX = event.rawX
                    initialTouchY = event.rawY
                    isClick = true

                    v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(100).start()
                    true
                }

                MotionEvent.ACTION_MOVE -> {
                    val deltaX = (event.rawX - initialTouchX).toInt()
                    val deltaY = (event.rawY - initialTouchY).toInt()

                    // If movement is significant, treat as drag, not click
                    if (isClick && (Math.abs(deltaX) > 10 || Math.abs(deltaY) > 10)) {
                        isClick = false
                    }

                    if (!isClick) {
                        layoutParams.x = initialX + deltaX
                        layoutParams.y = initialY + deltaY
                        windowManager.updateViewLayout(view, layoutParams)
                    }
                    true
                }

                MotionEvent.ACTION_UP -> {
                    v.animate().scaleX(1f).scaleY(1f).setDuration(100).start()

                    if (isClick) {
                        val audioManager =
                            context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
                        audioManager.adjustVolume(
                            AudioManager.ADJUST_SAME,
                            AudioManager.FLAG_SHOW_UI
                        )
                    }
                    true
                }

                else -> false
            }
        }

//        ball.setOnTouchListener { v, event ->
//            when (event.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(100).start()
//                    true
//                }
//                MotionEvent.ACTION_UP -> {
//                    v.animate().scaleX(1f).scaleY(1f).setDuration(100).start()
//                    Toast.makeText(context, "ball clicked", Toast.LENGTH_SHORT).show()
//
//                    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
//                    audioManager.adjustVolume(AudioManager.ADJUST_SAME, AudioManager.FLAG_SHOW_UI)
//
//                    true
//                }
//                else -> false
//            }
//        }

        try {
            windowManager.addView(view, layoutParams)
            Log.i(TAG, "Quick ball added to window.")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to add quick ball: ${e.message}", e)
        }

    }

    fun removeQuickBall() {
        Log.i(TAG, "removeQuickBall")
        view?.let {
            windowManager.removeView(it)
        }
        view = null

    }
}