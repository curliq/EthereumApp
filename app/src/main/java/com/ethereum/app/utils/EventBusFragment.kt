package com.ethereum.app.utils

import android.support.v4.app.Fragment

import org.greenrobot.eventbus.EventBus

/**
 * John was here on 30/06/2017, exactly by 22:14
 */

open class EventBusFragment : Fragment() {

    override fun onPause() {
        super.onPause()
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this)
    }

    override fun onStart() {
        super.onStart()
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this)
    }

}
