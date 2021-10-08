package com.mai.cybermoneywidget.network

import com.mai.cybermoneywidget.mmkv.MMKVUtils
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

enum class HttpUtil {
    INSTANCE;

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(3000, TimeUnit.MILLISECONDS)
            .readTimeout(3000, TimeUnit.MILLISECONDS)
            .build()
    }

    @Throws(Exception::class)
    fun getData(params: String): String? {
        val baseUrl = getBaseUrl() + params
        val request = Request.Builder().url(baseUrl).build()
        return try {
            client.newCall(request).execute().body!!.string()
        } catch (e: Exception) {
            throw e
        }
    }

    private fun getBaseUrl(): String {
        return when (MMKVUtils.INSTANCE.dataSource) {
            DataSource.Binance0 -> "https://api.binance.com/api/v3/ticker/price?symbol="
            DataSource.Binance1 -> "https://api1.binance.com/api/v3//ticker/price?symbol="
            DataSource.Binance2 -> "https://api2.binance.com/api/v3/ticker/price?symbol="
            DataSource.Binance3 -> "https://api3.binance.com/api/v3/ticker/price?symbol="
            DataSource.HuoBi0 -> "https://api.huobi.pro/market/detail/merged?symbol="
            DataSource.HuoBi1 -> "https://api-aws.huobi.pro/market/detail/merged?symbol="
            DataSource.SoChain -> "https://chain.so/api/v2/get_info/"
            DataSource.GateIO -> "https://data.gateapi.io/api2/1/ticker/"
            else -> ""
        }
    }
}