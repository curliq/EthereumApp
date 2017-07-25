package com.ether.checker.chart.model.POJOs

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



/**
 * John was here on 04/07/2017, exactly by 00:33
 */
class EtherChartData {

    @SerializedName("date")
    @Expose
    public val date: Long? = null
    @SerializedName("close")
    @Expose
    public val close: Double? = null


}