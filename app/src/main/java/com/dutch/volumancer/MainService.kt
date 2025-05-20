package com.dutch.volumancer

import android.app.Notification
import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat.startActivityForResult
import com.dutch.volumancer.VolumancerApplication.Companion.LOG_TAG
import androidx.core.net.toUri

class MainService : Service() {

    private lateinit var floatingBall: FloatingBall
    val TAG = "$LOG_TAG MainService"
    lateinit var notification: Notification


    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        Log.i(TAG, "onCreate()")
        super.onCreate()
        floatingBall = FloatingBall(this)
        buildForegroundNotification()
        startForeground(1, notification)
        if (!checkDrawOverlayPermission(this)) {
            requestDrawOverlayPermission(this)
            return // bail until permission is granted
        } else {
            floatingBall.showQuickBall()
        }

        floatingBall.showQuickBall()
    }

//    private fun buildForegroundNotification() {
//        Log.i(TAG, "buildForegroundNotification")
//        notification = Notification.Builder(this, "volumancer").setContentTitle("Volumancer")
//            .setContentText("Quick ball running").setSmallIcon(R.drawable.ic_launcher_background)
//            .build()
//    }

    private fun buildForegroundNotification() {
        Log.i(TAG, "buildForegroundNotification")

        val channelId = "volumancer_channel"
        val channelName = "Volumancer Service"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = android.app.NotificationChannel(
                channelId,
                channelName,
                android.app.NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Shows when Volumancer's quick ball is running"
            }

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
            manager.createNotificationChannel(channel)
        }

        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this, channelId)
        } else {
            Notification.Builder(this)
        }

        notification = builder
            .setContentTitle("Volumancer")
            .setContentText("Quick ball is active")
            .setSmallIcon(R.drawable.example_appwidget_preview) // ⚠️ Replace this with a real small icon
            .setOngoing(true)
            .build()
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "onStartCommand()")
        return super.onStartCommand(intent, flags, startId)

//        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy()")
        floatingBall.removeQuickBall()
    }


    fun checkDrawOverlayPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Settings.canDrawOverlays(context)
        } else true
    }

    fun requestDrawOverlayPermission(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(context)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                ("package:" + context.packageName).toUri()
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
}