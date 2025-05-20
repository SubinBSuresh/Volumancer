package com.dutch.volumancer

import android.content.Context
import android.media.AudioManager
import android.util.Log
import com.dutch.volumancer.VolumancerApplication.Companion.LOG_TAG

class MediaVolumeHelper(context: Context?) {

    val TAG = "$LOG_TAG com.dutch.volumancer.MediaVolumeHelper"
     val audioManager: AudioManager  = (context?.getSystemService(Context.AUDIO_SERVICE) ) as AudioManager;

    fun changeVolume(context: Context?, increase: Boolean) {
        Log.i(TAG, "changeVolume(), increase: $increase")
        val maximumVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        var currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

        currentVolume += if (increase) 7 else -7
        currentVolume = currentVolume.coerceIn(0, maximumVolume)
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0)

    }

    fun getCurrentVolume(context: Context?) : Int {
        return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
    }
}