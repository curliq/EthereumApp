package com.ether.checker.settings.screens

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ether.checker.R
import kotlinx.android.synthetic.main.activity_apis.*

/**
 * John was here on 22/07/2017, exactly by 17:52
 */
class ApisActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apis)

        apisBack.setOnClickListener({
            onBackPressed()
        })
    }

}