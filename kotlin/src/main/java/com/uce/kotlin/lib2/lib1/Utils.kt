package com.andreapivetta.blu.common.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.DisplayMetrics
import timber.log.Timber
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object Utils {

    fun dpToPx(context: Context, dp: Int) = Math.round(dp *
            (context.resources.displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))

    fun getBitmapFromURL(strURL: String): Bitmap? = try {
        val connection = URL(strURL).openConnection()
        connection.connect()
        BitmapFactory.decodeStream(connection.inputStream)
    } catch (e: IOException) {
        Timber.e(e)
        null
    }

    fun formatDate(timeStamp: Long): String? {
        val c = Calendar.getInstance()
        val c2 = Calendar.getInstance()
        c2.timeInMillis = timeStamp

        val diff = c.timeInMillis - timeStamp
        val seconds = TimeUnit.MILLISECONDS.toSeconds(diff)
        if (seconds > 60) {
            val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
            if (minutes > 60) {
                val hours = TimeUnit.MILLISECONDS.toHours(diff)
                if (hours > 24) {
                    if (c.get(Calendar.YEAR) == c2.get(Calendar.YEAR))
                        return SimpleDateFormat("MMM dd", Locale.getDefault()).format(c2.time)
                    else
                        return SimpleDateFormat("MMM dd yyyy", Locale.getDefault()).format(c2.time)
                } else
                    return "${hours}h"
            } else
                return "${minutes}m"
        } else
            return "${seconds}s"
    }

    fun inputStreamFromUri(context: Context, uri: Uri): InputStream
            = context.contentResolver.openInputStream(uri)

}
