package com.ether.checker.wallet.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ether.checker.MainActivity
import com.ether.checker.R
import com.ether.checker.utils.EventBusFragment
import com.ether.checker.utils.ShortCuts
import com.ether.checker.wallet.model.EventOnWalletsBalanceReceived
import com.ether.checker.wallet.model.POJOs.Wallet
import com.ether.checker.wallet.model.POJOs.WalletData
import com.ether.checker.wallet.model.WalletModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_wallet.view.*
import org.greenrobot.eventbus.Subscribe

/**
 * John was here on 29/06/2017, exactly by 00:12
 */

class WalletFragment : EventBusFragment() {

    var v : View? = null
    var walletData: WalletData? = null
    var pagesList: ArrayList<Wallet>? = null
    var pagesAdapter: WalletsPagerAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater!!.inflate(R.layout.fragment_wallet, container, false)
        (activity as MainActivity).setBottomItemSelected(MainActivity.Screens.WALLET.ordinal)
        WalletModel.requestForexRates(activity)

        init()

        if (walletData?.walletsList != null ) {
            showData(EventOnWalletsBalanceReceived(walletData?.walletsList))
            callBalances(walletData?.walletsList)
        }


        return v
    }

    private fun init() {

        walletData = Gson().fromJson(ShortCuts.getPrefs(activity).getString(activity.getString(R.string.wallet_data), null), WalletData::class.java)
        if (walletData == null) {
            walletData = WalletData()
            ShortCuts.getPrefs(activity).edit().putString(activity.getString(R.string.wallet_data), Gson().toJson(walletData)).apply()
        }

        if (walletData?.walletsList != null ) {
            pagesList = walletData?.walletsList
            pagesAdapter = WalletsPagerAdapter(childFragmentManager, pagesList)
            v?.walletScreenInfoLayout?.visibility = View.VISIBLE
            v?.walletScreenTabLayout?.setupWithViewPager(v?.walletScreenViewPager)
            v?.walletScreenViewPager?.adapter = pagesAdapter
        }


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
    }

    private fun callBalances(walletsList: ArrayList<Wallet>?) {

        if (walletsList!!.size > 0) {
            v?.walletLayoutPickOption?.visibility = View.GONE

            var allAddresses: String = ""

            walletsList
                    .filter { it.isFromAddress!! }
                    .forEach { allAddresses = allAddresses + "," + it.address!! }

            WalletModel.requestWalletBalance(activity, allAddresses)

        }
    }

    @Subscribe
    fun showData(event : EventOnWalletsBalanceReceived) {

        val pos = v?.walletScreenViewPager?.currentItem
        pagesList = event.addressesList
        pagesAdapter = WalletsPagerAdapter(fragmentManager, pagesList)
        v?.walletScreenViewPager?.adapter = pagesAdapter
        v?.walletScreenViewPager?.currentItem = pos!!
    }

}
