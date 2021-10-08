package com.mai.cybermoneywidget.widget

import android.appwidget.AppWidgetManager
import android.content.Context

class MainWidgetDark : BaseWidget() {
    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        mode = 0
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }
}