package com.covergenius.mca_sdk_android.views

sealed class Routes(val route: String) {
    object Health : Routes("Health")
}