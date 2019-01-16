package com.uce.kotlin.lib1

public inline fun async(action: () -> Unit): Unit = Thread(runnable(action)).start()
