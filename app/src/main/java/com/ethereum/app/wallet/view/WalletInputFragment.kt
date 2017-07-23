package com.ethereum.app.wallet.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ethereum.app.R
import com.ethereum.app.utils.ShortCuts
import com.ethereum.app.wallet.model.POJOs.Wallet
import com.ethereum.app.wallet.model.POJOs.WalletData
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_wallet_input.view.*
import java.util.*

/**
 * John was here on 30/06/2017, exactly by 20:31
 */

class WalletInputFragment : Fragment() {

    internal var isFromAddress = true

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_wallet_input, container, false)

        if (!arguments.getBoolean("inputAddress", false)) {
            isFromAddress = false
            view.fragmentInputEditText.hint = "Example: 66.666"
        }
        else
            view.fragmentInputEditText.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS



        view.fragmentInputConfirmButton.setOnClickListener {
            onConfirmClicked()
        }

        return view
    }

    fun onConfirmClicked() {

        val walletData = Gson().fromJson(ShortCuts.getPrefs(activity).getString(activity.getString(R.string.wallet_data), null), WalletData::class.java)
        walletData.walletsCount = walletData.walletsCount + 1
        val wallet = Wallet()
        wallet.isFromAddress = isFromAddress
        if (isFromAddress)
            wallet.address = view?.fragmentInputEditText?.text.toString()
        else {
            try {
                wallet.amount = view?.fragmentInputEditText!!.text.toString().toFloat()
            } catch (e: NumberFormatException) {
                Toast.makeText(activity, "That is not a valid number", Toast.LENGTH_SHORT).show()
                return
            }
        }
        wallet.name = if (view?.fragmentInputWalletName?.text.toString().isNotEmpty()) view?.fragmentInputWalletName?.text.toString() else "Wallet " + walletData.walletsCount

        if (walletData.walletsCount == 1)
            walletData.walletsList = ArrayList<Wallet>()
        walletData.walletsList!!.add(wallet)
        ShortCuts.getPrefs(activity).edit().putString(activity.getString(R.string.wallet_data), Gson().toJson(walletData)).commit()
        activity.onBackPressed()

    }

}
