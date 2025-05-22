package com.dutch.volumancer.core

import android.content.Context
import android.media.AudioManager
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify


class MediaVolumeHelperTest {

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var audioManager: AudioManager

    private lateinit var mediaVolumeHelper: MediaVolumeHelper


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        `when`(context.getSystemService(Context.AUDIO_SERVICE)).thenReturn(audioManager)
        mediaVolumeHelper = MediaVolumeHelper(context)

    }

    @Test
    fun `increase volume caps at max`(){
        `when`(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)).thenReturn(15)
        `when`(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)).thenReturn(5)

        mediaVolumeHelper.changeVolume(increase = true)

        // Expected volume = 5 + 7 = 12, which is within max 15
        verify(audioManager).setStreamVolume(AudioManager.STREAM_MUSIC, 12, 0)
    }
}