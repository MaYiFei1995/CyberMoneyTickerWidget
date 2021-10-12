//package com.mai.cybermoneywidget
//
//import android.app.Service
//import android.content.*
//import android.os.IBinder
//import android.util.Log
//import com.mai.cybermoneywidget.widget.BaseWidget
//import java.util.*
//
//class UpdateService : Service() {
//
//    private lateinit var timer: Timer
//    private lateinit var task: TimerTask
//
//    override fun onBind(p0: Intent?): IBinder? {
//        throw UnsupportedOperationException("Not yet implemented");
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//        timer = Timer()
//        task = object : TimerTask() {
//            override fun run() {
//                val intent = Intent()
//                intent.component = ComponentName(this@UpdateService, BaseWidget::class.java)
//                intent.action = BuildConfig.APPLICATION_ID + ".update_now"
//                Log.e("MaiTest", "sendBroadcast...action = ${intent.action}")
//                this@UpdateService.sendBroadcast(intent)
//            }
//        }
//        timer.schedule(task, 0, 5 * 60 * 1000L)
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        timer.cancel()
//        task.cancel()
//    }
//}