package com.ethereum.app.chart

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ethereum.app.MainActivity
import com.ethereum.app.R
import com.ethereum.app.chart.model.ChartModel
import com.ethereum.app.chart.model.EventOnEtherChartDataResponse
import com.ethereum.app.chart.model.EventOnEtherValueResponse
import com.ethereum.app.chart.model.POJOs.EtherChartData
import com.ethereum.app.chart.presenter.ChartAxisFormatterDate
import com.ethereum.app.utils.EventBusFragment
import com.ethereum.app.utils.ShortCuts
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_chart.view.*
import org.greenrobot.eventbus.Subscribe
import java.text.DecimalFormat
import java.util.*


/**
 * John was here on 29/06/2017, exactly by 00:12
 */

class ChartFragment : EventBusFragment() {

    public var chartInterval: Int = 300 //300 seconds I think

    var chart: LineChart? = null
    var rocketUpImage: ImageView? = null
    var rocketDownImage: ImageView? = null
    var changePercentageText: TextView? = null
    var etherValueText: TextView? = null
    var updatedText: TextView? = null
    var lastUpdateTime: Long = 0
    var marketCapText: TextView? = null
    var volumeText: TextView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_chart, container, false)
        (activity as MainActivity).setBottomItemSelected(MainActivity.Screens.CHART.ordinal)

        val etherChartData: ArrayList<EtherChartData>? = Gson().fromJson(ShortCuts.getPrefs(activity)
                .getString(activity.getString(R.string.chart_data), null), object : TypeToken<ArrayList<EtherChartData>>(){}.type)
        chart = v?.chartScreenChart
        rocketUpImage = v?.chartScreenRocketUp
        rocketDownImage = v?.chartScreenRocketDown
        changePercentageText = v?.chartScreenPercentageTextChange
        etherValueText = v?.chartScreenEtherValueText
        updatedText = v?.chartScreenUpdatedText
        marketCapText = v?.chartScreenMarketCapNumber
        volumeText = v?.chartScreenVolumeNumber

        val cal: Calendar = Calendar.getInstance()
        val now: Long = cal.timeInMillis / 1000
        cal.add(Calendar.MONTH, -1)
        val oneMonthAgo = cal.timeInMillis / 1000
        ChartModel.callChartData(activity, oneMonthAgo, now, chartInterval)

        if (ShortCuts.getPrefs(activity).getFloat(activity.getString(R.string.ethereum_vs_usd), 0f) > 0f)
        setEtherValue(EventOnEtherValueResponse(ShortCuts.getPrefs(activity).getFloat(activity.getString(R.string.ethereum_vs_usd), 0f),
                                                ShortCuts.getPrefs(activity).getLong(activity.getString(R.string.ether_requested_timestamp), 0),
                                                ShortCuts.getPrefs(activity).getFloat(activity.getString(R.string.ether_market_cap), 0f),
                                                ShortCuts.getPrefs(activity).getFloat(activity.getString(R.string.ether_volume), 0f),
                                                ShortCuts.getPrefs(activity).getFloat(activity.getString(R.string.ether_change_24h), 0f)))
        styleChart()
        setChartData(EventOnEtherChartDataResponse(etherChartData))



        object : CountDownTimer(99999999, 100) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                // do something every 5 seconds...
          //      lastUpdateTime -= 10000
                updatedText?.text = "updated " + getLastUpdatedTimeStamp(lastUpdateTime) + " ago"
            }

            override fun onFinish() {
                // finish off when we're all dead !
            }
        }.start()

        return v
    }

    private fun styleChart() {

        val xAxis = chart?.xAxis
        xAxis?.setDrawGridLines(false)
        xAxis?.position = XAxis.XAxisPosition.BOTTOM
        xAxis?.textColor = ContextCompat.getColor(activity, R.color.colorPrimaryDarker)
        xAxis?.setAvoidFirstLastClipping(true)
        xAxis?.labelCount = 5
        xAxis?.axisMinimum = 500f

        val leftAxis = chart?.axisLeft
        leftAxis?.textColor = ContextCompat.getColor(activity, R.color.colorPrimaryDarker)
        leftAxis?.disableGridDashedLine()
        leftAxis?.setDrawGridLines(false)
        leftAxis?.setDrawLimitLinesBehindData(false)

        chart?.legend?.isEnabled = false
        chart?.description = null
        chart?.setPinchZoom(true)
        chart?.setHardwareAccelerationEnabled(true)
        chart?.axisRight?.isEnabled = false
    }

    @SuppressLint("SetTextI18n")
    @Subscribe
    public fun setEtherValue(event: EventOnEtherValueResponse) {
        etherValueText?.text = "%.2f".format(event.ethValue)
        lastUpdateTime = event.timeStamp
        marketCapText?.text = DecimalFormat("#,###,###").format("%.0f".format(event.marketCap).toLong())
        volumeText?.text = DecimalFormat("#,###,###").format("%.0f".format(event.volume).toLong())

        if (event.change24h >= 0) {
            changePercentageText?.text = "+" + "%.2f".format(event.change24h) + "%"
            changePercentageText?.setTextColor(ContextCompat.getColor(activity, R.color.up_green))
            rocketUpImage?.visibility = View.VISIBLE
            rocketDownImage?.visibility = View.INVISIBLE
        }
        else {
            changePercentageText?.text = "%.2f".format(event.change24h) + "%"
            changePercentageText?.setTextColor(ContextCompat.getColor(activity, R.color.down_red))
            rocketUpImage?.visibility = View.INVISIBLE
            rocketDownImage?.visibility = View.VISIBLE
        }
    }

    @Subscribe
    public fun setChartData(event: EventOnEtherChartDataResponse) {

        Handler().post({

            if (event.etherChartData != null) {

                val xAxis = chart?.xAxis
                xAxis?.valueFormatter = ChartAxisFormatterDate(event.etherChartData)

                val entries = ArrayList<Entry>()

                for (i in 0..event.etherChartData.size.minus(1))
                    entries.add(Entry(i.toFloat(), event.etherChartData[i].close?.toFloat() as Float))


                val dataSet: LineDataSet = LineDataSet(entries, "")  // add entries to dataset


                dataSet.color = ContextCompat.getColor(activity, R.color.colorPrimary)
                dataSet.mode = LineDataSet.Mode.LINEAR
                dataSet.lineWidth = 1f
                dataSet.setDrawCircles(false)
                //dataSet.setDrawHighlightIndicators(true)
                //dataSet.setDrawHorizontalHighlightIndicator(false)
                dataSet.setDrawVerticalHighlightIndicator(true)
                dataSet.highLightColor = ContextCompat.getColor(activity, R.color.colorPrimaryDarker)
                dataSet.setDrawValues(false)
                dataSet.setDrawFilled(true)
                dataSet.fillDrawable = ContextCompat.getDrawable(activity, R.drawable.chart_gradient_fill)

                val lineData = LineData(dataSet)

                chart?.data = lineData
                chart?.invalidate()
              //  chart?.setVisibleXRangeMaximum(10000f)

            }

        })

    }

    private fun getLastUpdatedTimeStamp(timeUnix: Long): String {
        val cal: Calendar = Calendar.getInstance()
        val nowUnix: Long = cal.timeInMillis
        val diffSeconds: Long = (nowUnix - timeUnix) / 1000

        val diffMinutes: Long = diffSeconds / 60
        val diffHours: Long = diffMinutes / 24

        if (diffHours.toInt() == 1)
            return "1 hour"
        else if (diffHours > 0)
            return diffHours.toString() + " hours"

        if (diffMinutes.toInt() == 1)
            return "1 minute"
        else if (diffMinutes > 0)
            return diffMinutes.toString() + " minutes"

        if (diffSeconds.toInt() == 1)
            return diffSeconds.toString() + " second"
        else
            return diffSeconds.toString() + " seconds"
    }

    override fun onResume() {
        super.onResume()
        ChartModel.callEtherValue(activity)

        Handler().postDelayed({
            if (isResumed) {
                onResume()
            }
        }, 9000) //updated every 9 seconds, no special reason to be 9
    }
}
