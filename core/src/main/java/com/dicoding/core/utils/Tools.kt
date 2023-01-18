package com.dicoding.core.utils

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Parcelable
import android.widget.Toast

object Tools {
    fun toast(activity: Context?, text: String?) = Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
}

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}
