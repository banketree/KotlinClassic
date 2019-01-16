package com.uce.kotlin.lib5

import android.os.Handler

/**
 * Created by Kizito Nwose on 19/10/2017
 */

fun Handler.postDelayed(r: Runnable, delay: Interval<TimeUnit>)
        = postDelayed(r, delay.inMilliseconds.longValue)

fun Handler.postDelayed(r: () -> Unit, delay: Interval<TimeUnit>)
        = postDelayed(r, delay.inMilliseconds.longValue)

