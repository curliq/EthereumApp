package com.ethereum.app.wallet.view;

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.PopupMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.ethereum.app.MainActivity
import com.ethereum.app.R
import com.ethereum.app.utils.ShortCuts
import com.ethereum.app.wallet.model.EventOnWalletsBalanceReceived
import com.ethereum.app.wallet.model.POJOs.WalletData
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_wallet_add.view.*
import org.greenrobot.eventbus.EventBus


/**
 * John was here on 02/07/2017, exactly by 23:03
 */

class WalletsPageFragment : Fragment() {

    var v: View? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        if (arguments.getBoolean("isLastPage", false)) {

            v = inflater?.inflate(R.layout.fragment_wallet_add, container, false)

            v?.walletInputAddressButton?.setOnClickListener {
                val fragment = WalletInputFragment()
                val bundle = Bundle()
                bundle.putBoolean("inputAddress", true)
                fragment.arguments = bundle
                (activity as MainActivity).inflateFragmentFromTop(fragment)
            }

            v?.walletInputAmountButton?.setOnClickListener {
                val fragment = WalletInputFragment()
                val bundle = Bundle()
                bundle.putBoolean("inputAddress", false)
                fragment.arguments = bundle
                (activity as MainActivity).inflateFragmentFromTop(fragment)
            }
        } else {

            v = inflater?.inflate(R.layout.fragment_wallet_page, container, false)

            val ethAmount = arguments.getFloat("amountEther", 0f)

            val ethUsd = ShortCuts.getPrefs(activity).getFloat(activity.getString(R.string.ethereum_vs_usd), 0f)
            val ethBtc = ShortCuts.getPrefs(activity).getFloat(activity.getString(R.string.ethereum_vs_btc), 0f)
            val ethGbp = ShortCuts.getPrefs(activity).getFloat(activity.getString(R.string.usd_vs_gbp), 0f) * ethUsd
            val ethEur = ShortCuts.getPrefs(activity).getFloat(activity.getString(R.string.usd_vs_eur), 0f) * ethUsd

            v?.findViewById<TextView>(R.id.walletPageAddress)?.text = arguments.getString("address", "").toString()
            v?.findViewById<TextView>(R.id.walletPageBalanceEther)?.text = ethAmount.toString()
            v?.findViewById<TextView>(R.id.walletScreenUsdAmmount)?.text = ethAmount.times(ethUsd).toString()
            v?.findViewById<TextView>(R.id.walletScreenGbpAmount)?.text = ethAmount.times(ethGbp).toString()
            v?.findViewById<TextView>(R.id.walletScreenEurAmount)?.text = ethAmount.times(ethEur).toString()
            v?.findViewById<TextView>(R.id.walletScreenBitcoinAmount)?.text = ethAmount.times(ethBtc).toString()

            setDropdownMenu()

        }

        return v

    }

    fun setDropdownMenu() {
        val dots = v?.findViewById<ImageButton>(R.id.walletPageDots)
        val popup = PopupMenu(activity, dots as View)
        popup.menuInflater.inflate(R.menu.wallet_dropdown_menu, popup.menu)

        try {
            val classPopupMenu = Class.forName(popup.javaClass.name)
            val mPopup = classPopupMenu.getDeclaredField("mPopup")
            mPopup.isAccessible = true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.walletMenuDelete -> {
                    val walletData = Gson().fromJson(ShortCuts.getPrefs(activity).getString(activity.getString(R.string.wallet_data), null), WalletData::class.java)
                    walletData.walletsList?.remove(walletData.walletsList!![arguments.getInt("position")])
                    ShortCuts.getPrefs(activity).edit().putString(activity.getString(R.string.wallet_data), Gson().toJson(walletData)).apply()
                    EventBus.getDefault().post(EventOnWalletsBalanceReceived(walletData?.walletsList))
                }
            }
            true
        }

        dots.setOnClickListener { popup.show() }
    }
}