package com.ethereum.app.settings.screens

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.LinearLayout
import com.ethereum.app.R
import kotlinx.android.synthetic.main.activity_about.*

/**
 * John was here on 22/07/2017, exactly by 17:52
 */
class AboutActivity : AppCompatActivity() {

    var backLayout: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        backLayout = aboutBack

        backLayout?.setOnClickListener({
            onBackPressed()
        })

    }

}