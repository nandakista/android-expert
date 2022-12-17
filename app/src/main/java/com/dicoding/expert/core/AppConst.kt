package com.dicoding.expert.core

import com.dicoding.expert.BuildConfig

object AppConst {
    const val SPLASH_TIME_MILLISECOND: Long = 1500

    const val DB_NAME = "user_github.db"
    const val DB_VERSION = 1

    // Error Message
    const val userNotFound = "User not found..."
    const val appVer = "Ⓒ Gitfind - Version ${BuildConfig.VERSION_NAME}"
}