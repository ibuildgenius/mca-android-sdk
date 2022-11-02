package com.covergenius.mca_sdk_android.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.covergenius.mca_sdk_android.domain.model.Credential
import com.covergenius.mca_sdk_android.domain.model.Form
import com.covergenius.mca_sdk_android.domain.model.PaymentOption
import com.covergenius.mca_sdk_android.domain.model.TransactionType
import com.covergenius.mca_sdk_android.presentation.theme.McasdkandroidTheme
import dagger.hilt.android.AndroidEntryPoint

object MyCoverAI {
    fun init(
        context: Context,
        credential: Credential = Credential(
            "",
            "",
            "",
            Form("", "", ""),
            "",
            TransactionType.Inspection,
            PaymentOption.Gateway
        )
    ) {
        context.startActivity(Intent(context, MainActivity::class.java))
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            McasdkandroidTheme {
                MyCoverApp()
            }
        }
    }
}