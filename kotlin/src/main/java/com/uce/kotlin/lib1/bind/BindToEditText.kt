package com.marcinmoskala.kotlinandroidviewbindings

import android.app.Activity
import android.app.Fragment
import android.os.Build
import android.support.annotation.IdRes
import android.support.annotation.RequiresApi
import android.widget.EditText
import android.widget.FrameLayout
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun Activity.bindToEditText(@IdRes editTextId: Int): ReadWriteProperty<Any?, String>
        = bindToEditTextP { findViewById(editTextId) as EditText }

@RequiresApi(Build.VERSION_CODES.HONEYCOMB)
fun Fragment.bindToEditText(@IdRes viewId: Int): ReadWriteProperty<Any?, String>
        = bindToEditTextP { view.findViewById(viewId) as EditText }

fun android.support.v4.app.Fragment.bindToEditText(@IdRes viewId: Int): ReadWriteProperty<Any?, String>
        = bindToEditTextP { view!!.findViewById(viewId) as EditText }

fun FrameLayout.bindToEditText(@IdRes viewId: Int): ReadWriteProperty<Any?, String>
        = bindToEditTextP { findViewById(viewId) as EditText }

fun EditText.bindToEditText(): ReadWriteProperty<Any?, String>
        = bindToEditTextP { this }

fun Activity.bindToEditText(viewProvider: () -> EditText): ReadWriteProperty<Any?, String>
        = bindToEditTextP { viewProvider() }

@RequiresApi(Build.VERSION_CODES.HONEYCOMB)
fun Fragment.bindToEditText(viewProvider: () -> EditText): ReadWriteProperty<Any?, String>
        = bindToEditTextP { viewProvider() }

fun android.support.v4.app.Fragment.bindToEditText(viewProvider: () -> EditText): ReadWriteProperty<Any?, String>
        = bindToEditTextP { viewProvider() }

private fun bindToEditTextP(viewProvider: () -> EditText): ReadWriteProperty<Any?, String>
        = EditTextViewTextBinding(lazy(viewProvider))

private class EditTextViewTextBinding(lazyViewProvider: Lazy<EditText>) : ReadWriteProperty<Any?, String> {

    val view by lazyViewProvider

    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return view.text.toString()
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        view.setText(value)
    }
}