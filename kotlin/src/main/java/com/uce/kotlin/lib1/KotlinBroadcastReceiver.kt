package com.uce.kotlin.lib1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

public inline fun BroadcastReceiver(init: (Context?, Intent?) -> Unit): BroadcastReceiver {
    return object : BroadcastReceiver() {
        public override fun onReceive(p0: Context?, p1: Intent?) {
            init(p0, p1)
        }
    }
}
