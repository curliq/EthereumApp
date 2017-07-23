package com.ethereum.app.chart.presenter

import android.text.format.DateFormat
import com.ethereum.app.chart.model.POJOs.EtherChartData
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import java.util.*

/**
 * John was here on 09/07/2017, exactly by 23:09
 */
class ChartAxisFormatterDate(val dates: ArrayList<EtherChartData>?) : IAxisValueFormatter {


    override fun getFormattedValue(value: Float, axis: AxisBase?): String {

        return getDateText(dates?.get(value.toInt())?.date as Long)
    }

    private fun getDateText(dateMillis: Long): String {
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = dateMillis * 1000

        return DateFormat.format("d MMM", calendar).toString()
    }
}