package com.ether.checker.wallet.model.POJOs

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * John was here on 09/07/2017, exactly by 16:00
 */
class ForexRatesResponse {

    @SerializedName("base")
    @Expose
    public var base: String? = null
    @SerializedName("date")
    @Expose
    public var date: String? = null
    @SerializedName("rates")
    @Expose
    public var rates: Rates? = null

    public class Rates {

        @SerializedName("AUD")
        @Expose
        public var aUD: Double? = null
        @SerializedName("BGN")
        @Expose
        public var bGN: Double? = null
        @SerializedName("BRL")
        @Expose
        public var bRL: Double? = null
        @SerializedName("CAD")
        @Expose
        public var cAD: Double? = null
        @SerializedName("CHF")
        @Expose
        public var cHF: Double? = null
        @SerializedName("CNY")
        @Expose
        public var cNY: Double? = null
        @SerializedName("CZK")
        @Expose
        public var cZK: Double? = null
        @SerializedName("DKK")
        @Expose
        public var dKK: Double? = null
        @SerializedName("GBP")
        @Expose
        public var gBP: Double? = null
        @SerializedName("HKD")
        @Expose
        public var hKD: Double? = null
        @SerializedName("HRK")
        @Expose
        public var hRK: Double? = null
        @SerializedName("HUF")
        @Expose
        public var hUF: Double? = null
        @SerializedName("IDR")
        @Expose
        public var iDR: Double? = null
        @SerializedName("ILS")
        @Expose
        public var iLS: Double? = null
        @SerializedName("INR")
        @Expose
        public var iNR: Double? = null
        @SerializedName("JPY")
        @Expose
        public var jPY: Double? = null
        @SerializedName("KRW")
        @Expose
        public var kRW: Double? = null
        @SerializedName("MXN")
        @Expose
        public var mXN: Double? = null
        @SerializedName("MYR")
        @Expose
        public var mYR: Double? = null
        @SerializedName("NOK")
        @Expose
        public var nOK: Double? = null
        @SerializedName("NZD")
        @Expose
        public var nZD: Double? = null
        @SerializedName("PHP")
        @Expose
        public var pHP: Double? = null
        @SerializedName("PLN")
        @Expose
        public var pLN: Double? = null
        @SerializedName("RON")
        @Expose
        public var rON: Double? = null
        @SerializedName("RUB")
        @Expose
        public var rUB: Double? = null
        @SerializedName("SEK")
        @Expose
        public var sEK: Double? = null
        @SerializedName("SGD")
        @Expose
        public var sGD: Double? = null
        @SerializedName("THB")
        @Expose
        public var tHB: Double? = null
        @SerializedName("TRY")
        @Expose
        public var tRY: Double? = null
        @SerializedName("ZAR")
        @Expose
        public var zAR: Double? = null
        @SerializedName("EUR")
        @Expose
        public var eUR: Double? = null

    }

}