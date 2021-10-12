package com.mai.cybermoneywidget.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.mai.cybermoneywidget.App
import com.mai.cybermoneywidget.BuildConfig
import com.mai.cybermoneywidget.R
//import com.mai.cybermoneywidget.UpdateService
import com.mai.cybermoneywidget.mmkv.MMKVUtils

open class BaseWidget : AppWidgetProvider() {

    var mode = 0

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        if (intent!!.action == "${BuildConfig.APPLICATION_ID}.update_now") {
            mode = intent.getIntExtra("style_mode", 0)
            refreshView(context!!, mode)
        }
//        else {
//            val intent2 = Intent(context, UpdateService::class.java)
//            context?.startService(intent2);
//        }
    }

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
//        val intent = Intent(context, UpdateService::class.java)
//        context?.startService(intent);
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
//        val intent = Intent(context, UpdateService::class.java)
//        context?.stopService(intent);
    }

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        Log.e("MaiTest", "onUpdate")
        refreshView(context!!, mode)
    }

    private fun refreshView(context: Context, mode: Int) {
        val remoteViews = RemoteViews(
            App.app.packageName,
            if (mode == 0) R.layout.main_widget_layout_dark else R.layout.main_widget_layout_light
        )

        if (MMKVUtils.INSTANCE.btc.isEmpty() || (System.currentTimeMillis() - MMKVUtils.INSTANCE.timeStamp > 5 * 59 * 1000L)) {
            Thread {
                App.app.updateData(object : App.UpdateDataCallback {
                    override fun onSuccess() {
                        updateData(context, remoteViews)
                    }

                    override fun onFailure(e: String) {
                        updateData(context, remoteViews)
                    }
                })
            }.start()
        } else {
            updateData(context, remoteViews)
        }
    }

    private fun updateData(context: Context, remoteViews: RemoteViews) {
        Log.e("MaiTest", "updateData")

        remoteViews.setTextViewText(R.id.btc_tv, MMKVUtils.INSTANCE.btc)
        remoteViews.setTextViewText(R.id.eth_tv, MMKVUtils.INSTANCE.eth)

        val intent = Intent("${BuildConfig.APPLICATION_ID}.update_now")
        intent.component = ComponentName(context, this::class.java)
        intent.putExtra("style_mode", mode)
        val pendingIntent =
            PendingIntent.getBroadcast(context, 9527, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        remoteViews.setOnClickPendingIntent(R.id.main_widget, pendingIntent)

        AppWidgetManager.getInstance(context)
            .updateAppWidget(ComponentName(context, this::class.java), remoteViews)
    }
}