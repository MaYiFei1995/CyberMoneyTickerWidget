package com.mai.cybermoneywidget.mmkv

import com.mai.cybermoneywidget.network.DataSource
import com.tencent.mmkv.MMKV

enum class MMKVUtils {
    INSTANCE;

    val keyTimeStamp = "TimeStamp"
    val keyDataSource = "DataSource"
    val keyBTC = "BTC"
    val keyETH = "ETH"

    private val sp = MMKV.defaultMMKV()

    private fun getString(key: String, defaultValue: String = ""): String {
        return sp.decodeString(key, defaultValue)!!
    }

    private fun setString(key: String, value: String) {
        sp.encode(key, value)
    }

    private fun getLong(key: String, defaultValue: Long = 0L): Long {
        return sp.decodeLong(key, defaultValue)
    }

    private fun setLong(key: String, value: Long) {
        sp.encode(key, value)
    }

    var timeStamp: Long = 0L
        get() {
            if (field == 0L) {
                field = getLong(keyTimeStamp, 0L)
            }
            return field
        }
        set(value) {
            setLong(keyTimeStamp, value)
            field = value
        }

    var dataSource: String = ""
        get() {
            if (field.isEmpty())
                field = getString(keyDataSource, DataSource.GateIO)
            return field
        }
        set(value) {
            setString(keyDataSource, value)
            field = value
        }

    var btc: String = ""
        get() {
            if (field.isEmpty())
                field = getString(keyBTC)
            return field
        }
        set(value) {
            setString(keyBTC, value)
            field = value
        }

    var eth: String = ""
        get() {
            if (field.isEmpty())
                field = getString(keyETH)
            return field
        }
        set(value) {
            setString(keyETH, value)
            field = value
        }

}