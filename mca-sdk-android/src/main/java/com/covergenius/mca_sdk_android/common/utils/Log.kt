package com.covergenius.mca_sdk_android.common.utils

import android.util.Log

/**
 * Logging Util for MCA SDK with remote config support
 * */
object Log {
    const val PREFIX = "CoverGenius"
    fun d(tag: String, message: String) {
        Log.d("$PREFIX-$tag", message)
    }

    fun i(tag: String, message: String) {
        Log.i("$PREFIX-$tag", message)
    }

    fun e(tag: String, message: String, t: Throwable?) {
        Log.e("$PREFIX-$tag", message, t)
    }
}

enum class LogLevel {
    DEBUG,
    INFO,
    ERROR
}