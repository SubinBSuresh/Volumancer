package com.dutch.volumancer

import android.app.Application
import android.util.Log

class VolumancerApplication : Application() {
	private val TAG = "$LOG_TAG VolumancerApplication"

	override fun onCreate() {
		try {
			Log.i(TAG, "onCreate() context: ${this.applicationContext}")
		} catch (e: Exception) {
			Log.e(TAG, e.toString())
		}
		super.onCreate()
		ServiceStarter().register(this)
//        startService(Intent(this, MainService::class.java))

//        Handler(Looper.getMainLooper()).post {
//            applicationContext?.let {
//                Log.i(TAG, "Safe start: context ready")
//                ContextCompat.startForegroundService(it, Intent(it, MainService::class.java))
//            } ?: Log.w(TAG, "Context is STILL null bro 💀")
//
//        }
	}

	companion object {
		const val LOG_TAG = "Volumancer"
		var quickBallEnabled = false
	}
}
