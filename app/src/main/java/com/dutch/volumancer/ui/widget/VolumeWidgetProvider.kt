package com.dutch.volumancer.ui.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import androidx.core.content.ContextCompat
import com.dutch.volumancer.service.MainService
import com.dutch.volumancer.R
import com.dutch.volumancer.VolumancerApplication
import com.dutch.volumancer.core.AppConstants
import com.dutch.volumancer.core.MediaVolumeHelper

class VolumeWidgetProvider : AppWidgetProvider() {
	val TAG = "${VolumancerApplication.Companion.LOG_TAG} com.dutch.volumancer.VolumeWidgetProvider"
	lateinit var mediaVolumeHelper: MediaVolumeHelper

	override fun onReceive(context: Context, intent: Intent?) {
		Log.i(TAG, "onReceive(), intent: $intent context: $context")
		mediaVolumeHelper = MediaVolumeHelper(context)

		super.onReceive(context, intent)

		when (intent?.action) {
			AppConstants.Companion.ACTION_VOLUME_UP -> mediaVolumeHelper.changeVolume( true)
			AppConstants.Companion.ACTION_VOLUME_DOWN -> mediaVolumeHelper.changeVolume( false)
			AppConstants.Companion.ACTION_QUICk_BALL_TOGGLE -> {
				Log.i(TAG, "quickBallEnabled: ${VolumancerApplication.Companion.quickBallEnabled}")
				if (VolumancerApplication.Companion.quickBallEnabled) {
					context.stopService(Intent(context, MainService::class.java))
					VolumancerApplication.Companion.quickBallEnabled = false
				} else {
					ContextCompat.startForegroundService(context,
						Intent(context, MainService::class.java)
					)
					VolumancerApplication.Companion.quickBallEnabled = true
				}
			}
		}
		updateAllWidgets(context!!)
	}

	override fun onUpdate(
		context: Context?,
		appWidgetManager: AppWidgetManager?,
		appWidgetIds: IntArray?,
	) {
		Log.i(TAG, "onUpdate()")
		super.onUpdate(context, appWidgetManager, appWidgetIds)
		appWidgetIds?.forEach { widgetId ->
			updateAllWidgets(context!!)
		}
	}

	fun updateAllWidgets(context: Context) {
		val widgetManager = AppWidgetManager.getInstance(context)

		context?.let {
			val componentName = ComponentName(it, VolumeWidgetProvider::class.java)
			val ids = AppWidgetManager.getInstance(it).getAppWidgetIds(componentName)
			ids.forEach { updateWidget(context, widgetManager, it) }
		}
	}

	private fun updateWidget(context: Context?, widgetManager: AppWidgetManager, widgetId: Int) {
		Log.i(TAG, "updateWidget()")

		val views = RemoteViews(context?.packageName, R.layout.widget_layout)

		val volume = mediaVolumeHelper.getCurrentVolume()
		views.setTextViewText(R.id.tv_widgetMedia, "Volume: $volume")

		val upIntent = Intent(context, VolumeWidgetProvider::class.java).apply {
			action = AppConstants.Companion.ACTION_VOLUME_UP
		}

		val downIntent = Intent(context, VolumeWidgetProvider::class.java).apply {
			action = AppConstants.Companion.ACTION_VOLUME_DOWN
		}

		val quickBall = Intent(context, VolumeWidgetProvider::class.java).apply {
			action = AppConstants.Companion.ACTION_QUICk_BALL_TOGGLE
		}

		views.setOnClickPendingIntent(
			R.id.btn_widgetPlus,
			PendingIntent.getBroadcast(
				context,
				0,
				upIntent,
				PendingIntent.FLAG_IMMUTABLE,
			),
		)

		views.setOnClickPendingIntent(
			R.id.btn_WidgetMinus,
			PendingIntent.getBroadcast(
				context,
				1,
				downIntent,
				PendingIntent.FLAG_IMMUTABLE,
			),
		)

		views.setOnClickPendingIntent(
			R.id.tv_widgetMedia,
			PendingIntent.getBroadcast(
				context,
				2,
				quickBall,
				PendingIntent.FLAG_IMMUTABLE,
			),
		)

		widgetManager.updateAppWidget(widgetId, views)
	}
}
