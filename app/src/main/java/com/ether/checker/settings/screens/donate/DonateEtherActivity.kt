package com.ether.checker.settings.screens.donate

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ether.checker.R
import kotlinx.android.synthetic.main.activity_donate_ether.*



/**
 * John was here on 22/07/2017, exactly by 20:53
 */
class DonateEtherActivity : AppCompatActivity() {

    var donationsWalletAddress: String = "0x49F414F73af16c527dEdBf8FB76F894c67d36055"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate_ether)

        sendEtherBack.setOnClickListener({
            onBackPressed()
        })

        sendEtherWalletText.text = donationsWalletAddress

        sendEtherShare.setOnClickListener({
            val sharingIntent = Intent(android.content.Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here")
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, donationsWalletAddress)
            startActivity(Intent.createChooser(sharingIntent, "Send wallet address"))
        })
    }
}