package com.dutch.volumancer

import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
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
        layoutParams.x = 100
        layoutParams.y = 300

        view = LayoutInflater.from(context).inflate(R.layout.quick_ball, null)
        val ball = view!!.findViewById<View>(R.id.ball)

        ball.setOnClickListener{
            Toast.makeText(context, "ball clicked", Toast.LENGTH_SHORT).show()
        }
        windowManager.addView(view, layoutParams)

    }

    fun removeQuickBall() {
        Log.i(TAG, "removeQuickBall")
        view?.let {
            windowManager.removeView(it)
        }
        view = null

    }





}