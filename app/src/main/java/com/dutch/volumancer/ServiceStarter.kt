package com.dutch.volumancer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.dutch.volumancer.VolumancerApplication.Companion.LOG_TAG

class ServiceStarter : BroadcastReceiver() {
	private val TAG = "$LOG_TAG ServiceStarter"

	override fun onReceive(context: Context?, intent: Intent?) {
		Log.i(TAG, "onReceive(), intent: $intent")
		if (context != null) {
			val serviceIntent = Intent(context, MainService::class.java)
			ContextCompat.startForegroundService(context, serviceIntent)
		}
	}

	fun register(context: Context) {
		val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
		val result = audioManager.requestAudioFocus(
			{ focusChange ->
				Log.i(TAG, "Audio focus changed: $focusChange")

				// Start service here
				val serviceIntent = Intent(context, MainService::class.java)
				ContextCompat.startForegroundService(context, serviceIntent)
			},
			AudioManager.STREAM_MUSIC,
			AudioManager.AUDIOFOCUS_GAIN,
		)

		Log.i(TAG, "Audio focus request result: $result")
	}
}
