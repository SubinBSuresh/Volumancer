package com.dutch.volumancer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dutch.volumancer.VolumancerApplication.Companion.LOG_TAG

class MainActivity : AppCompatActivity() {
    private val TAG = "$LOG_TAG MainActivity"


    private lateinit var btnStartQuickBallService: Button
    private lateinit var btnStopQuickBallService: Button
    private lateinit var btnShutdown: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate()")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initViews()
        setClickListeners()


    }

    private fun setClickListeners() {
        Log.i(TAG, "setClickListeners")
        btnShutdown.setOnClickListener {
            stopService(Intent(this, MainService::class.java))
            finishAndRemoveTask()
        }
        btnStartQuickBallService.setOnClickListener {
            ContextCompat.startForegroundService(this, Intent(this, MainService::class.java))
        }
        btnStopQuickBallService.setOnClickListener {
            val stopIntent = Intent(this, MainService::class.java)
            stopService(stopIntent)
        }
    }

    private fun initViews() {
        Log.i(TAG, "initViews()")
        btnShutdown = findViewById(R.id.btn_shutdown)
        btnStartQuickBallService = findViewById(R.id.btn_startQuickBallService)
        btnStopQuickBallService = findViewById(R.id.btn_stopQuickBallService)
    }
}