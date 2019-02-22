package com.andreapivetta.blu.common.utils

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import android.support.customtabs.CustomTabsIntent
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.andreapivetta.blu.R
import com.andreapivetta.blu.ui.custom.Theme
import com.andreapivetta.blu.ui.hashtag.HashtagActivity
import com.andreapivetta.blu.ui.profile.UserActivity
import com.bumptech.glide.Glide
import com.luseen.autolinklibrary.AutoLinkMode
import com.luseen.autolinklibrary.AutoLinkTextView

/**
 * Created by andrea on 27/09/16.
 */
fun View.visible(show: Boolean = true) {
    this.visibility = if (show) View.VISIBLE else View.GONE
}

fun ImageView.loadUri(uri: Uri?, @DrawableRes placeholder: Int = R.drawable.placeholder) {
    Glide.with(context).load(uri).placeholder(placeholder).into(this)
}

fun ImageView.loadUrl(url: CharSequence?, @DrawableRes placeholder: Int = R.drawable.placeholder) {
    Glide.with(context).load(url).placeholder(placeholder).into(this)
}

fun ImageView.loadUrlWithoutPlaceholder(url: CharSequence?) {
    Glide.with(context).load(url).into(this)
}

fun ImageView.loadUrlCenterCrop(url: CharSequence?, @DrawableRes placeholder: Int = R.drawable.placeholder) {
    Glide.with(context).load(url).placeholder(placeholder).centerCrop().into(this)
}

fun ImageView.loadAvatar(url: CharSequence?) {
    // TODO placeholder
    Glide.with(context).load(url).dontAnimate().into(this)
}

fun AutoLinkTextView.setupText(text: String) {
    addAutoLinkMode(AutoLinkMode.MODE_HASHTAG, AutoLinkMode.MODE_URL, AutoLinkMode.MODE_MENTION)
    setHashtagModeColor(Theme.getColorPrimary(context))
    setUrlModeColor(Theme.getColorPrimary(context))
    setMentionModeColor(Theme.getColorPrimary(context))
    setAutoLinkText(text)
    setAutoLinkOnClickListener { mode, text ->
        when (mode) {
            AutoLinkMode.MODE_HASHTAG -> HashtagActivity.launch(context, text)
            AutoLinkMode.MODE_MENTION -> UserActivity.launch(context, text)
            AutoLinkMode.MODE_URL -> openUrl(context as Activity, text)
            else -> throw UnsupportedOperationException("No handlers for mode $mode")
        }
    }
}

fun AppCompatActivity.pushFragment(@LayoutRes containerViewId: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction().replace(containerViewId, fragment).commit()
}

fun openUrl(activity: Activity, url: String) {
    try {
        CustomTabsIntent.Builder()
                .setToolbarColor(Theme.getColorPrimary(activity))
                .setShowTitle(true)
                .addDefaultShareMenuItem()
                .build()
                .launchUrl(activity, Uri.parse(url.trim()))
    } catch (err: Exception) {
        Toast.makeText(activity,
                "You are running this app on an alien spaceship and we don't support them yet",
                Toast.LENGTH_SHORT).show()
    }
}

fun shareText(context: Context, text: String) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.putExtra(Intent.EXTRA_TEXT, text)
    intent.type = "text/plain"
    context.startActivity(intent)
}

fun shareApp(context: Context) {
    try {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri
                .parse("market://details?id=${context.applicationContext.packageName}")))
    } catch (err: Exception) {
        Toast.makeText(context, context.getString(R.string.missing_play_store), Toast.LENGTH_SHORT).show()
    }
}

fun download(context: Context, url: String) {
    val request = DownloadManager.Request(Uri.parse(url))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir("/Download", System.currentTimeMillis().toString())

    request.allowScanningByMediaScanner()

    (context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager).enqueue(request)
}