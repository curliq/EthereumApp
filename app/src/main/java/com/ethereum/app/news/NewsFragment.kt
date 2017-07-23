package com.ethereum.app.news

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ethereum.app.MainActivity
import com.ethereum.app.R

/**
 * John was here on 29/06/2017, exactly by 00:13
 */

class NewsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_news, container, false)
        (activity as MainActivity).setBottomItemSelected(MainActivity.Screens.NEWS.ordinal)


        return view
    }
}
