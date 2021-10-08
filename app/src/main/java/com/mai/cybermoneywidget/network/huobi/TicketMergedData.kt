package com.mai.cybermoneywidget.network.huobi

class TicketMergedData {

    /**
     * 数据所属的 channel，格式：market.$symbol.detail.merged
     */
    var ch:String = ""

    /**
     * 请求处理结果 "ok","error"
     */
    var status: String = ""

    /**
     * 响应生成时间点，单位：毫秒
     */
    var ts: Long = 0L
    var tick: TickData? = null
}