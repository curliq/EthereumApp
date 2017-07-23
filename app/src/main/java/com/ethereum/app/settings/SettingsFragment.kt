package com.ethereum.app.settings

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.ethereum.app.MainActivity
import com.ethereum.app.R
import com.ethereum.app.settings.screens.AboutActivity
import com.ethereum.app.settings.screens.ApisActivity
import com.ethereum.app.settings.screens.DonateActivity
import com.ethereum.app.settings.screens.LibrariesActivity
import kotlinx.android.synthetic.main.fragment_settings.view.*

/**
 * John was here on 29/06/2017, exactly by 00:13
 */

class SettingsFragment : Fragment() {

    var aboutText: LinearLayout? = null
    var donateText: LinearLayout? = null
    var apisText: LinearLayout? = null
    var librariesText: LinearLayout? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_settings, container, false)
        (activity as MainActivity).setBottomItemSelected(MainActivity.Screens.SETTINGS.ordinal)

        aboutText = v.settingsAbout
        donateText = v.settingsDonate
        apisText = v.settingApis
        librariesText = v.settingLibraries

        aboutText?.setOnClickListener({
            startActivity(Intent(activity, AboutActivity::class.java))
        })

        donateText?.setOnClickListener({
            startActivity(Intent(activity, DonateActivity::class.java))
        })

        apisText?.setOnClickListener({
            startActivity(Intent(activity, ApisActivity::class.java))
        })

        librariesText?.setOnClickListener({
            startActivity(Intent(activity, LibrariesActivity::class.java))
        })

        return v
    }
}
