package com.ethereum.app.wallet.model.POJOs

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * John was here on 30/06/2017, exactly by 22:11
 */

class WalletsBalancesResponse {

    @SerializedName("status")
    @Expose
    var status: Int = 0

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("result")
    @Expose
    var result: ArrayList<WalletObjectResponse>? = null


    class WalletObjectResponse {

        @SerializedName("account")
        @Expose
        var account: String? = null

        @SerializedName("balance")
        @Expose
        var balance: Long? = null

    }
}
