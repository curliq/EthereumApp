package com.ethereum.app.settings.screens

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.ethereum.app.R
import com.ethereum.app.settings.screens.donate.DonateEtherActivity
import com.ethereum.app.settings.screens.donate.DonateFiatActivity
import kotlinx.android.synthetic.main.activity_donate.*

/**
 * John was here on 22/07/2017, exactly by 17:52
 */
class DonateActivity : AppCompatActivity() {

    var donateLayout: LinearLayout? = null
    var sendEtherLayout: RelativeLayout? = null
    var sendPaypalLayout: RelativeLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate)

        donateLayout = donateBack
        sendEtherLayout = donateSendEther
        sendPaypalLayout = donatePaypal

        donateLayout?.setOnClickListener({
            onBackPressed()
        })

        sendEtherLayout?.setOnClickListener({
            startActivity(Intent(this, DonateEtherActivity::class.java))
        })

        sendPaypalLayout?.setOnClickListener({
            startActivity(Intent(this, DonateFiatActivity::class.java))
        })

    }

}