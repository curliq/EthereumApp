package com.ethereum.app.settings.screens

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ethereum.app.R
import kotlinx.android.synthetic.main.activity_libraries.*


/**
 * John was here on 22/07/2017, exactly by 17:52
 */
class LibrariesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_libraries)

        librariesBack.setOnClickListener({
            onBackPressed()
        })

        settingsLibrariesMPAndroidChart.setOnClickListener({
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/PhilJay/MPAndroidChart"))
            startActivity(browserIntent)
        })

        settingsLibrariesRetrofit.setOnClickListener({
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/square/retrofit"))
            startActivity(browserIntent)
        })

        settingsLibrariesEventBus.setOnClickListener({
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/greenrobot/EventBus"))
            startActivity(browserIntent)
        })
    }

}