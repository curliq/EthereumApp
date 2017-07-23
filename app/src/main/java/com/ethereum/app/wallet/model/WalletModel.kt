package com.ethereum.app.wallet.model

import android.content.Context
import com.ethereum.app.R
import com.ethereum.app.utils.ShortCuts
import com.ethereum.app.wallet.model.POJOs.ForexRatesResponse
import com.ethereum.app.wallet.model.POJOs.WalletData
import com.ethereum.app.wallet.model.POJOs.WalletsBalancesResponse
import com.google.gson.Gson
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * John was here on 29/06/2017, exactly by 22:36
 */

object WalletModel {

    fun requestForexRates(context: Context) {

        ShortCuts.getRetrofit(ShortCuts.FIXER_BASE_URL).create(WalletInterface::class.java).getForexRates("USD").enqueue(object : Callback<ForexRatesResponse> {

            override fun onResponse(call: Call<ForexRatesResponse>?, response: Response<ForexRatesResponse>?) {
                ShortCuts.getPrefs(context).edit().putFloat(context.getString(R.string.usd_vs_eur), response?.body()?.rates?.eUR?.toFloat()!!).apply()
                ShortCuts.getPrefs(context).edit().putFloat(context.getString(R.string.usd_vs_gbp), response.body()?.rates?.gBP?.toFloat()!!).apply()
            }

            override fun onFailure(call: Call<ForexRatesResponse>?, t: Throwable?) {

            }
        })
    }


    fun requestWalletBalance(context: Context, addresses: String) {

        ShortCuts.getRetrofit(ShortCuts.ETHERSCAN_BASE_URL).create(WalletInterface::class.java).getWalletBalances("account",
                "balancemulti", addresses, "latest", ShortCuts.ETHERSCAN_API_KEY).enqueue(object : Callback<WalletsBalancesResponse> {

            override fun onResponse(call: Call<WalletsBalancesResponse>, response: Response<WalletsBalancesResponse>) {

                val walletData: WalletData = Gson().fromJson(ShortCuts.getPrefs(context).getString(context.getString(R.string.wallet_data), ""), WalletData::class.java)

                if (response.code() == 200) {

                    for (savedWallet in walletData.walletsList!!) {
                        response.body().result!!
                                .filter { savedWallet.address.equals(it.account) }
                                .forEach { savedWallet.amount = it.balance?.div(1000000000000000000f) }
                    }
                }

                ShortCuts.getPrefs(context).edit().putString(context.getString(R.string.wallet_data), Gson().toJson(walletData)).apply()

                EventBus.getDefault().post(EventOnWalletsBalanceReceived(walletData.walletsList))

            }

            override fun onFailure(call: Call<WalletsBalancesResponse>, t: Throwable) {

            }
        })
    }


    internal interface WalletInterface {

        /*
         * https://api.etherscan.io/api?module=account&action=balancemulti&address=0xddbd2b932c763ba5b1b7ae3b362eac3e8d40121a,0x63a9975ba31b0b9626b34300f7f627147df1f526,0x198ef1ec325a96cc354c7266a038be8b5c558f67&tag=latest&apikey=YourApiKeyToken
         */

        @GET("api")
        fun getWalletBalances(@Query("module") module: String,
                             @Query("action") action: String,
                             @Query("address") addresses: String,
                             @Query("tag") tag: String,
                             @Query("apikey") apiKey: String): Call<WalletsBalancesResponse>

        @GET("latest")
        fun getForexRates(@Query("base") baseCurrency: String): Call<ForexRatesResponse>

    }

}
