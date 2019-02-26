package me.eugeniomarletti.extras.bundle.base

import android.os.Bundle
import android.os.Parcelable
import me.eugeniomarletti.extras.TypeReader
import me.eugeniomarletti.extras.TypeWriter
import me.eugeniomarletti.extras.bundle.BundleExtra
import me.eugeniomarletti.extras.bundle.Generic
import java.io.Serializable

inline fun <T> BundleExtra.Bundle(
    crossinline reader: TypeReader<T, Bundle?>,
    crossinline writer: TypeWriter<T, Bundle?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        Bundle::getBundle,
        Bundle::putBundle,
        reader,
        writer,
        name,
        customPrefix)

inline fun <T> BundleExtra.CharSequence(
    crossinline reader: TypeReader<T, CharSequence?>,
    crossinline writer: TypeWriter<T, CharSequence?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        Bundle::getCharSequence,
        Bundle::putCharSequence,
        reader,
        writer,
        name,
        customPrefix)

inline fun <T> BundleExtra.String(
    crossinline reader: TypeReader<T, String?>,
    crossinline writer: TypeWriter<T, String?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        Bundle::getString,
        Bundle::putString,
        reader,
        writer,
        name,
        customPrefix)

inline fun <T, R : Parcelable> BundleExtra.Parcelable(
    crossinline reader: TypeReader<T, R?>,
    crossinline writer: TypeWriter<T, R?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        Bundle::getParcelable,
        Bundle::putParcelable,
        reader,
        writer,
        name,
        customPrefix)

inline fun <T, R : Serializable> BundleExtra.Serializable(
    crossinline reader: TypeReader<T, R?>,
    crossinline writer: TypeWriter<T, R?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        { name ->
            @Suppress("UNCHECKED_CAST")
            getSerializable(name) as? R?
        },
        Bundle::putSerializable,
        reader,
        writer,
        name,
        customPrefix)
