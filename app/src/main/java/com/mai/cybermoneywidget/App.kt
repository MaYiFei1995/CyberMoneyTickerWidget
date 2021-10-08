package com.mai.cybermoneywidget

import android.app.Application
import com.google.gson.Gson
import com.mai.cybermoneywidget.mmkv.MMKVUtils
import com.mai.cybermoneywidget.network.DataSource
import com.mai.cybermoneywidget.network.HttpUtil
import com.mai.cybermoneywidget.network.binance.BinanceRetData
import com.mai.cybermoneywidget.network.gateio.GateIORetData
import com.mai.cybermoneywidget.network.huobi.TicketMergedData
import com.mai.cybermoneywidget.network.sochain.SoChainRetData
import com.tencent.mmkv.MMKV

class App : Application() {

    companion object {
        lateinit var app: App
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        MMKV.initialize(app)
    }

    fun updateData(callback: UpdateDataCallback, forceUpdate: Boolean = false) {
        if (!forceUpdate) {
            if (System.currentTimeMillis() - MMKVUtils.INSTANCE.timeStamp < 5 * 60 * 1000L) {
                callback.onFailure("force update == false")
            }
        }

        val dataSource = MMKVUtils.INSTANCE.dataSource
        when {
            dataSource.startsWith(DataSource.Binance) -> {
                var isError = 0
                var errMsg = ""
                try {
                    val retBtc = HttpUtil.INSTANCE.getData("BTCUSDT")
                    val data = Gson().fromJson(retBtc, BinanceRetData::class.java)
                    if (data.price.isNotEmpty()) {
                        MMKVUtils.INSTANCE.btc =
                            "${data.price.substring(0, data.price.indexOf('.') + 3)}$"
                    }

                    MMKVUtils.INSTANCE.timeStamp = System.currentTimeMillis()

                } catch (e: Exception) {
                    val s = "on get btc from Binance error...e = $e"
                    errMsg += s
                    isError = 1
                }

                try {
                    val retEth = HttpUtil.INSTANCE.getData("ETHUSDT")
                    val data = Gson().fromJson(retEth, BinanceRetData::class.java)
                    if (data.price.isNotEmpty()) {
                        MMKVUtils.INSTANCE.eth =
                            "${data.price.substring(0, data.price.indexOf('.') + 3)}$"
                    }

                    MMKVUtils.INSTANCE.timeStamp = System.currentTimeMillis()

                } catch (e: Exception) {
                    val s =
                        "${if (isError == 1) "\n" else ""}on get eth from Binance error...e = $e"
                    errMsg += s
                    isError += 1
                }

                if (isError == 2) {
                    callback.onFailure(errMsg)
                } else
                    callback.onSuccess()
            }

            dataSource.startsWith(DataSource.HuoBi) -> {
                var isError = 0
                var errMsg = ""
                try {
                    val retBtc = HttpUtil.INSTANCE.getData("btcusdt")
                    val data = Gson().fromJson(retBtc, TicketMergedData::class.java)

                    if (data.status == "ok") {
                        data.tick?.let {
                            MMKVUtils.INSTANCE.btc = "${it.close}$"
                        }
                    }

                    if (data.ts > 0L) {
                        MMKVUtils.INSTANCE.timeStamp = data.ts
                    }
                } catch (e: Exception) {
                    val s = "on get btc from HuoBi error...e = $e"
                    errMsg += s
                    isError = 1
                }

                try {
                    val retEth = HttpUtil.INSTANCE.getData("ethusdt")
                    val data = Gson().fromJson(retEth, TicketMergedData::class.java)

                    if (data.status == "ok") {
                        data.tick?.let {
                            MMKVUtils.INSTANCE.eth = "${it.close}$"
                        }
                    }

                    if (data.ts > 0L) {
                        MMKVUtils.INSTANCE.timeStamp = data.ts
                    }
                } catch (e: Exception) {
                    val s = "${if (isError == 1) "\n" else ""}on get eth from HuoBi error...e = $e"
                    errMsg += s
                    isError += 1
                }

                if (isError == 2) {
                    callback.onFailure(errMsg)
                } else
                    callback.onSuccess()
            }


            dataSource.startsWith(DataSource.SoChain) -> {
                var isError = 0
                var errMsg = ""
                try {
                    val retBtc = HttpUtil.INSTANCE.getData("BTC")
                    val data = Gson().fromJson(retBtc, SoChainRetData::class.java)
                    if (data.data.price.isNotEmpty()) {
                        MMKVUtils.INSTANCE.btc =
                            "${data.data.price.substring(0, data.data.price.indexOf('.') + 3)}$"
                        MMKVUtils.INSTANCE.timeStamp = data.data.price_update_time * 1000L
                    }
                } catch (e: Exception) {
                    val s = "on get btc from SoChain error...e = $e"
                    errMsg += s
                    isError = 1
                }

                if (isError == 1) {
                    callback.onFailure(errMsg)
                } else
                    callback.onSuccess()
            }

            dataSource.startsWith(DataSource.GateIO) -> {
                var isError = 0
                var errMsg = ""
                try {
                    val retBtc = HttpUtil.INSTANCE.getData("btc_usdt")
                    val data = Gson().fromJson(retBtc, GateIORetData::class.java)
                    if (data.result && data.last.isNotEmpty()) {
                        MMKVUtils.INSTANCE.btc =
                            "${data.last}$"
                        MMKVUtils.INSTANCE.timeStamp = System.currentTimeMillis()
                    }
                } catch (e: Exception) {
                    val s = "on get btc from GateIO error...e = $e"
                    errMsg += s
                    isError = 1
                }

                try {
                    val retEth = HttpUtil.INSTANCE.getData("eth_usdt")
                    val data = Gson().fromJson(retEth, GateIORetData::class.java)

                    if (data.result && data.last.isNotEmpty()) {
                        MMKVUtils.INSTANCE.eth =
                            "${data.last}$"
                        MMKVUtils.INSTANCE.timeStamp = System.currentTimeMillis()
                    }
                } catch (e: Exception) {
                    val s = "${if (isError == 1) "\n" else ""}on get eth from GateIO error...e = $e"
                    errMsg += s
                    isError += 1
                }

                if (isError == 2) {
                    callback.onFailure(errMsg)
                } else
                    callback.onSuccess()
            }
        }
    }

    interface UpdateDataCallback {
        fun onSuccess()
        fun onFailure(e: String)
    }
}