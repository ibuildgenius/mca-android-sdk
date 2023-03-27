package com.covergenius.mca_sdk_android.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.covergenius.mca_sdk_android.presentation.theme.McasdkandroidTheme
import dagger.hilt.android.AndroidEntryPoint

object MyCoverAI {
    fun init(
        context: Context,
        token: String
    ) {
        context.startActivity(Intent(context, MainActivity::class.java).putExtra("user-sdk-token", token))
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val token = intent.getStringExtra("user-sdk-token")

        setContent {
            McasdkandroidTheme {
                MyCoverApp(token)
            }
        }
    }
}