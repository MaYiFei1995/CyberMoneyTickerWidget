package com.mai.cybermoneywidget

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mai.cybermoneywidget.databinding.ActivityMainBinding
import com.mai.cybermoneywidget.mmkv.MMKVUtils
import com.mai.cybermoneywidget.network.DataSource
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var logStr: String = ""

    private lateinit var binder: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binder.root)

        binder.dataSource.check(
            when (MMKVUtils.INSTANCE.dataSource) {
                DataSource.Binance0 -> R.id.dataSourceBinance0
                DataSource.Binance1 -> R.id.dataSourceBinance0
                DataSource.Binance2 -> R.id.dataSourceBinance0
                DataSource.Binance3 -> R.id.dataSourceBinance0
                DataSource.HuoBi0 -> R.id.dataSourceBinance0
                DataSource.HuoBi1 -> R.id.dataSourceBinance0
                DataSource.SoChain -> R.id.dataSourceSoChain
                else -> R.id.dataSourceGateIO
            }
        )

        binder.dataSource.setOnCheckedChangeListener { _, id ->
            when (id) {
                R.id.dataSourceBinance0 -> {
                    MMKVUtils.INSTANCE.dataSource = DataSource.Binance0
                }
                R.id.dataSourceBinance1 -> {
                    MMKVUtils.INSTANCE.dataSource = DataSource.Binance0
                }
                R.id.dataSourceBinance2 -> {
                    MMKVUtils.INSTANCE.dataSource = DataSource.Binance0
                }
                R.id.dataSourceBinance3 -> {
                    MMKVUtils.INSTANCE.dataSource = DataSource.Binance0
                }
                R.id.dataSourceHuoBi0 -> {
                    MMKVUtils.INSTANCE.dataSource = DataSource.HuoBi0
                }
                R.id.dataSourceHuoBi1 -> {
                    MMKVUtils.INSTANCE.dataSource = DataSource.HuoBi0
                }
                R.id.dataSourceSoChain -> {
                    MMKVUtils.INSTANCE.dataSource = DataSource.SoChain
                }
                R.id.dataSourceGateIO -> {
                    MMKVUtils.INSTANCE.dataSource = DataSource.GateIO
                }
            }
        }

        binder.updateBtn.setOnClickListener {
            Thread {
                App.app.updateData(object : App.UpdateDataCallback {
                    override fun onSuccess() {
                        this@MainActivity.runOnUiThread {
                            refreshData()
                        }
                    }

                    override fun onFailure(e: String) {
                        this@MainActivity.runOnUiThread {
                            showError(e)
                        }
                    }

                }, true)
            }.start()
        }

        if(MMKVUtils.INSTANCE.timeStamp == 0L)
            binder.updateBtn.performClick()
            else
        refreshData()

    }

    private fun showError(e: String) {
        setText(e)
    }

    private fun refreshData() {
        val log = "DataSource:${MMKVUtils.INSTANCE.dataSource}" +
                "\nBTC-USDT: ${MMKVUtils.INSTANCE.btc}" +
                "\nETH-USDT: ${MMKVUtils.INSTANCE.eth}" +
                "\nUpdateTime:${
                    SimpleDateFormat(
                        "MM-dd HH:mm:ss.SSS",
                        Locale.getDefault()
                    ).format(MMKVUtils.INSTANCE.timeStamp)
                }"

        setText(log, true)
    }

    private fun setText(log: String, clear: Boolean = false) {
        Log.e("Mai", "onSetText...log=$log")
        if (clear) {
            logStr = ""
        }
        logStr += "$log\n\n"

        binder.inputArea.text = logStr
    }
}