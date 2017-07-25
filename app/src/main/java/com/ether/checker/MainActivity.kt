package com.ether.checker

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.ether.checker.chart.ChartFragment
import com.ether.checker.settings.SettingsFragment
import com.ether.checker.wallet.view.WalletFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    enum class Screens {
        CHART, NEWS, WALLET, SETTINGS
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inflateFragmentNoBackStack(ChartFragment())

        bottomChartItem.setOnClickListener { inflateFragment(ChartFragment()) }

        //bottomNewsItem.setOnClickListener { inflateFragment(NewsFragment()) }

        bottomWalletItem.setOnClickListener { inflateFragment(WalletFragment()) }

        bottomSettingsItem.setOnClickListener { inflateFragment(SettingsFragment()) }


    }


    fun inflateFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.mainContainer, fragment, "")
                .addToBackStack(null)
                .commit()
    }

    fun inflateFragmentNoBackStack(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.mainContainer, fragment)
                .commit()
    }

    fun inflateFragmentFromTop(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.in_from_bottom, R.anim.out_to_top, R.anim.in_from_top, R.anim.out_to_bottom)
                .replace(R.id.mainContainer, fragment)
                .addToBackStack(null)
                .commit()

    }

    fun setBottomItemSelected(position: Int) {

        if (position == Screens.CHART.ordinal) {
            bottomChartItem!!.alpha = 1f
          //  bottomNewsItem!!.alpha = 0.3f
            bottomWalletItem!!.alpha = 0.3f
            bottomSettingsItem!!.alpha = 0.3f
        }

        if (position == Screens.NEWS.ordinal) {
            bottomChartItem!!.alpha = 0.3f
           // bottomNewsItem!!.alpha = 1f
            bottomWalletItem!!.alpha = 0.3f
            bottomSettingsItem!!.alpha = 0.3f
        }

        if (position == Screens.WALLET.ordinal) {
            bottomChartItem!!.alpha = 0.3f
           // bottomNewsItem!!.alpha = 0.3f
            bottomWalletItem!!.alpha = 1f
            bottomSettingsItem!!.alpha = 0.3f
        }

        if (position == Screens.SETTINGS.ordinal) {
            bottomChartItem!!.alpha = 0.3f
           // bottomNewsItem!!.alpha = 0.3f
            bottomWalletItem!!.alpha = 0.3f
            bottomSettingsItem!!.alpha = 1f
        }

    }

}
