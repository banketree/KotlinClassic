package com.uce.kotlin.lib1

import android.app.Activity
import android.view.View

public inline fun Activity.findView<T: View>(id: Int): T? = findViewById(id) as? T

public inline fun Activity.runOnUiThread(action: () -> Unit): Unit {
    runOnUiThread(runnable(action))
}
