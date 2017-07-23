package com.ethereum.app.chart.model

import android.content.Context
import com.ethereum.app.R
import com.ethereum.app.chart.model.POJOs.EthValueResponse
import com.ethereum.app.chart.model.POJOs.EtherChartData
import com.ethereum.app.utils.ShortCuts
import com.google.gson.Gson
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

/**
 * John was here on 02/07/2017, exactly by 22:21
 */
object ChartModel {

    fun callEtherValue(context: Context) {
        ShortCuts.getRetrofit(ShortCuts.COINMARKETCAP_BASE_URL).create(ChartInterface::class.java)
                .getEthereumValue()
                .enqueue(object : Callback<ArrayList<EthValueResponse>> {

            override fun onResponse(call: Call<ArrayList<EthValueResponse>>?, response: Response<ArrayList<EthValueResponse>>?) {

                val timeStamp: Long = Calendar.getInstance().timeInMillis

                ShortCuts.getPrefs(context).edit().putFloat(context.getString(R.string.ethereum_vs_usd), response?.body()?.get(0)?.priceUsd!!.toFloat()).apply()
                ShortCuts.getPrefs(context).edit().putFloat(context.getString(R.string.ethereum_vs_btc), response.body()?.get(0)?.priceBtc!!.toFloat()).apply()
                ShortCuts.getPrefs(context).edit().putLong(context.getString(R.string.ether_requested_timestamp), timeStamp).apply()
                ShortCuts.getPrefs(context).edit().putFloat(context.getString(R.string.ether_market_cap), response.body()?.get(0)?.marketCapUsd!!.toFloat()).apply()
                ShortCuts.getPrefs(context).edit().putFloat(context.getString(R.string.ether_volume), response.body()?.get(0)?._24hVolumeUsd!!.toFloat()).apply()
                ShortCuts.getPrefs(context).edit().putFloat(context.getString(R.string.ether_change_24h), response.body()?.get(0)?.percentChange24h!!.toFloat()).apply()


                EventBus.getDefault().post(EventOnEtherValueResponse(response.body()?.get(0)?.priceUsd!!.toFloat(),
                                                                     timeStamp,
                                                                     response.body()?.get(0)?.marketCapUsd!!.toFloat(),
                                                                     response.body()?.get(0)?._24hVolumeUsd!!.toFloat(),
                                                                     response.body()?.get(0)?.percentChange24h!!.toFloat()))
            }

            override fun onFailure(call: Call<ArrayList<EthValueResponse>>?, t: Throwable?) {
            }
        })

    }

    fun callChartData(context: Context, startTime: Long, endTime: Long, interval: Int) {
        ShortCuts.getRetrofit(ShortCuts.POLONIEX_BASE_URL).create(ChartInterface::class.java)
                .getChartData("returnChartData", "USDT_ETH", startTime, endTime, interval)
                .enqueue(object : Callback<ArrayList<EtherChartData>> {

            override fun onResponse(call: Call<ArrayList<EtherChartData>>?, response: Response<ArrayList<EtherChartData>>?) {
                ShortCuts.getPrefs(context).edit().putString(context.getString(R.string.chart_data), Gson().toJson(response?.body())).apply()
                EventBus.getDefault().post(EventOnEtherChartDataResponse(response?.body()))
            }

            override fun onFailure(call: Call<ArrayList<EtherChartData>>?, t: Throwable?) {

            }
        })
    }

    internal interface ChartInterface {

        /*
         * coinmarketcap
         */

        @GET("v1/ticker/ethereum/")
        fun getEthereumValue(): Call<ArrayList<EthValueResponse>>

        /*
         * https://poloniex.com/support/api/
         */

        @GET("public/")
        fun getChartData(@Query("command") command: String,
                         @Query("currencyPair") pair: String,
                         @Query("start") startTime: Long,
                         @Query("end") endTime: Long,
                         @Query("period") period: Int): Call<ArrayList<EtherChartData>>

    }

}