package com.dutch.volumancer.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PixelFormat
import android.media.AudioManager
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import com.dutch.volumancer.R
import com.dutch.volumancer.VolumancerApplication

class FloatingBall(private val context: Context) {
	private val TAG = "${VolumancerApplication.Companion.LOG_TAG} FloatingBall"

	private val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
	private var view: View? = null
	private var layoutParams: WindowManager.LayoutParams? = null

	// auto hide components
	private val autoHideDelay: Long = 3000
	private val handler = Handler(Looper.getMainLooper())
	private val autoHideRunnable = Runnable {
		view?.animate()?.alpha(0.3f)?.setDuration(300)?.start()
	}

	@SuppressLint("ClickableViewAccessibility")
	fun showQuickBall() {
		if (view != null) return
		Log.i(TAG, "showQuickBall")

		layoutParams = WindowManager.LayoutParams(
			WindowManager.LayoutParams.WRAP_CONTENT,
			WindowManager.LayoutParams.WRAP_CONTENT,
			WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
			WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
			PixelFormat.TRANSLUCENT,
		).apply {
			gravity = Gravity.TOP or Gravity.START
			x = 10
			y = 10
		}

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
			resetAutoHideTimer()
			when (event.action) {
				MotionEvent.ACTION_DOWN -> {
					initialX = layoutParams!!.x
					initialY = layoutParams!!.y
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
						layoutParams?.x = initialX + deltaX
						layoutParams?.y = initialY + deltaY
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
							AudioManager.FLAG_SHOW_UI,
						)
					} else {
						val screenWidth = context.resources.displayMetrics.widthPixels
						layoutParams?.x =
							if (layoutParams!!.x + v.width / 2 < screenWidth / 2) 0 else screenWidth - v.width
						windowManager.updateViewLayout(view, layoutParams)
					}
					true
				}

				else -> false
			}
		}
		try {
			windowManager.addView(view, layoutParams)
			Log.i(TAG, "Quick ball added to window.")
			resetAutoHideTimer()
		} catch (e: Exception) {
			Log.e(TAG, "Failed to add quick ball: ${e.message}", e)
		}
	}

	private fun resetAutoHideTimer() {
		Log.i(TAG, "resetAutoHideTimer")
		handler.removeCallbacks(autoHideRunnable)
		view?.animate()?.alpha(1f)?.setDuration(200)?.start()
		handler.postDelayed(autoHideRunnable, autoHideDelay)
	}

	fun removeQuickBall() {
		Log.i(TAG, "removeQuickBall")
		view?.let {
			handler.removeCallbacks(autoHideRunnable)
			windowManager.removeView(it)
		}
		view = null
	}
}
