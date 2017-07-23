package com.ethereum.app.wallet.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.ethereum.app.wallet.model.POJOs.Wallet

/**
 * John was here on 02/07/2017, exactly by 23:02
 */
class WalletsPagerAdapter(fm: FragmentManager, val walletsList: ArrayList<Wallet>?) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        val bundle : Bundle = Bundle()
        val pageFragment : WalletsPageFragment = WalletsPageFragment()

        bundle.putBoolean("isLastPage", position == walletsList!!.size)
        bundle.putInt("position", position)
        if (position < walletsList.size) {
            bundle.putString("address", walletsList[position].address)
            if (walletsList[position].amount != null) //added by address and amount hadn't been queried
                bundle.putFloat("amountEther", walletsList[position].amount!!.toFloat())
        }

        pageFragment.arguments = bundle
        return pageFragment
    }

    override fun getCount(): Int {
        return walletsList!!.size + 1
    }

    override fun getPageTitle(position: Int): CharSequence? {
        if (position == walletsList!!.size)
            return "Add Wallet"
        return walletsList[position].name
    }

}