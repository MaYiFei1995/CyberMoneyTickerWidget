package com.mai.cybermoneywidget.widget

import android.appwidget.AppWidgetManager
import android.content.Context

class MainWidgetLight : BaseWidget() {
    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        mode = 1
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }
}