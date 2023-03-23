package com.covergenius.mca_sdk_android.presentation.views.payment

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.covergenius.mca_sdk_android.presentation.views.components.MyCoverButton
import com.covergenius.mca_sdk_android.presentation.theme.colorSpaceGray
import com.covergenius.mca_sdk_android.R

@Composable
fun PaymentResult(proceedTo: () -> Unit) {

    Column(Modifier.padding(12.dp)) {
        Column(
            Modifier
                .fillMaxSize()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                modifier = Modifier.size(75.dp),
                painter = painterResource(id = R.drawable.payment_success),
                contentDescription = ""
            )

            Box(modifier = Modifier.height(10.dp))

            Text(
                "Purchase Successful",
                style = MaterialTheme.typography.body2.copy(fontSize = 22.sp),
                textAlign = TextAlign.Center
            )
            Box(modifier = Modifier.height(10.dp))
            Text(
                "You have successfully completed\n" +
                        "your purchase,\n" +
                        "Kindly Check your email\n" +
                        "to complete your activation",
                style = MaterialTheme.typography.h1.copy(colorSpaceGray, lineHeight = 20.sp),
                textAlign = TextAlign.Center
            )
        }
        MyCoverButton("Done", proceedTo)

    }
}