package com.covergenius.mca_sdk_android.utils

import android.util.Log

/**
 * Logging Util for MCA SDK with remote config support
 * */
object Log {
    fun d(tag: String, message: String) {

    }

    fun i(tag: String, message: String) {

    }

    fun e(tag: String, message: String, t: Throwable?) {
        Log.e(tag, message, t)
    }
}