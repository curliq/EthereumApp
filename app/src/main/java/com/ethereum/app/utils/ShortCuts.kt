package com.ethereum.app.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.ethereum.app.R
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * John was here on 29/06/2017, exactly by 22:14
 */

object ShortCuts {

    val ETHERSCAN_API_KEY = "7RK8NWIBHFZR5EMIY62F8IT66KPNB8YWG1"
    val ETHERSCAN_BASE_URL = "https://api.etherscan.io/"
    val COINMARKETCAP_BASE_URL = "https://api.coinmarketcap.com/"
    val POLONIEX_BASE_URL = "https://poloniex.com/"
    val FIXER_BASE_URL = "http://api.fixer.io/"

    fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(context.getString(R.string.preferences_key), Context.MODE_PRIVATE)
    }

    fun getRetrofit(sourceBaseUrl: String): Retrofit {
        val builder = OkHttpClient().newBuilder()
        builder.readTimeout(20, TimeUnit.SECONDS)
        builder.connectTimeout(20, TimeUnit.SECONDS)

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(logging)

        val client = builder.build()

        return Retrofit.Builder()
                .baseUrl(sourceBaseUrl)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .client(client)
                .build()
    }
    

    fun log(msg: String) {
        Log.i("tagg", "" + msg)
    }

}
