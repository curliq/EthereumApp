package com.ether.checker.chart.model.POJOs

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName





/**
 * John was here on 02/07/2017, exactly by 22:25
 */
class EthValueResponse {


    @SerializedName("id")
    @Expose
    public val id: String? = null
    @SerializedName("name")
    @Expose
    public val name: String? = null
    @SerializedName("symbol")
    @Expose
    public val symbol: String? = null
    @SerializedName("rank")
    @Expose
    public val rank: String? = null
    @SerializedName("price_usd")
    @Expose
    public val priceUsd: String? = null
    @SerializedName("price_btc")
    @Expose
    public val priceBtc: String? = null
    @SerializedName("24h_volume_usd")
    @Expose
    public val _24hVolumeUsd: String? = null
    @SerializedName("market_cap_usd")
    @Expose
    public val marketCapUsd: String? = null
    @SerializedName("available_supply")
    @Expose
    public val availableSupply: String? = null
    @SerializedName("total_supply")
    @Expose
    public val totalSupply: String? = null
    @SerializedName("percent_change_1h")
    @Expose
    public val percentChange1h: String? = null
    @SerializedName("percent_change_24h")
    @Expose
    public val percentChange24h: String? = null
    @SerializedName("percent_change_7d")
    @Expose
    public val percentChange7d: String? = null
    @SerializedName("last_updated")
    @Expose
    public val lastUpdated: String? = null

}