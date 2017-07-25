package com.ether.checker.settings.screens.donate

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ether.checker.R
import kotlinx.android.synthetic.main.activity_donate_fiat.*

/**
 * John was here on 22/07/2017, exactly by 20:54
 */
class DonateFiatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate_fiat)

        sendFiatBack.setOnClickListener({
            onBackPressed()
        })
    }
}