package com.dutch.volumancer.core

import android.content.Context
import android.media.AudioManager
import android.util.Log
import com.dutch.volumancer.VolumancerApplication

class MediaVolumeHelper(context: Context?) {
	val volumeStep = 7

	val TAG = "${VolumancerApplication.Companion.LOG_TAG} com.dutch.volumancer.MediaVolumeHelper"
	val audioManager: AudioManager = (context?.getSystemService(Context.AUDIO_SERVICE)) as AudioManager

	fun changeVolume(increase: Boolean) {
		Log.i(TAG, "changeVolume(), increase: $increase")
		val maximumVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
		var currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

		currentVolume += if (increase) volumeStep else - volumeStep
		currentVolume = currentVolume.coerceIn(0, maximumVolume)
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0)
	}

	fun getCurrentVolume(): Int {
		return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
	}
}
