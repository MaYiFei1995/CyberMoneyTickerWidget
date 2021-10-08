package com.mai.cybermoneywidget.network.huobi

class TickData {

    /**
     * NA
     */
    var id: Long = 0L

    /**
     * 以基础币种计量的交易量（以滚动24小时计）
     */
    var amount: Float = 0f

    /**
     * 交易次数（以滚动24小时计）
     */
    var count: Int = 0

    /**
     * 本阶段开盘价（以滚动24小时计）
     */
    var open: Float = 0f

    /**
     * 本阶段最新价（以滚动24小时计）
     */
    var close: Float = 0f

    /**
     * 本阶段最低价（以滚动24小时计）
     */
    var low: Float = 0f

    /**
     * 本阶段最高价（以滚动24小时计）
     */
    var high: Float = 0f

    /**
     * 以报价币种计量的交易量（以滚动24小时计）
     */
    var vol: Float = 0f

}