package com.covergenius.mca_sdk_android.views.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.covergenius.mca_sdk_android.data.model.PaymentMethod
import com.covergenius.mca_sdk_android.views.theme.*
import com.covergenius.mca_sdk_android.utils.Separator
import com.covergenius.mca_sdk_android.views.composables.MyCoverButton
import com.covergenius.mca_sdk_android.views.composables.MyCoverTemplate
import com.covergenius.mca_sdk_android.views.composables.PaymentType

@Composable
fun PaymentScreen() {

    var formStep by remember { mutableStateOf(0) }
    var buttonText by remember { mutableStateOf("Continue") }

    MyCoverTemplate(
        content = {
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxHeight()
            ) {
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            colorPrimaryBg
                        )
                        .padding(8.dp)
                ) {
                    Text(
                        "chuks@gmail.com",
                        style = MaterialTheme.typography.subtitle1.copy(color = colorGrey)
                    )
                    Box(modifier = Modifier.height(4.dp))
                    Row {
                        Text("Pay", style = MaterialTheme.typography.body1.copy(color = colorGrey))
                        Box(Modifier.width(4.dp))
                        Text("N6,500.00", style = MaterialTheme.typography.body2.copy(colorPrimary))
                    }
                }

                Box(modifier = Modifier.height(20.dp))

                if (formStep == 0) {
                    StepOne()
                } else {
                    StepTwo()
                }


                Box(
                    Modifier
                        .fillMaxHeight()
                        .weight(1f)
                ) {
                    Separator(Modifier.align(Alignment.Center))
                }

                MyCoverButton(buttonText = buttonText, onPressed = {
                    if (formStep == 0) {
                        formStep++
                        buttonText = "I have sent the money"
                    }
                })

            }
        }
    )
}


@Composable
fun StepOne() {

    var paymentMethod by remember { mutableStateOf(PaymentMethod.Transfer) }


    Column {

        Text(
            "Select Payment method",
            style = MaterialTheme.typography.body2.copy(
                fontSize = 21.sp,
                color = colorNavyBlue
            )
        )
        Box(Modifier.height(3.dp))
        Text(
            "Choose an option to proceed",
            style = MaterialTheme.typography.subtitle1.copy(color = colorGrey)
        )

        Box(Modifier.height(10.dp))

        PaymentType(
            isSelected = (paymentMethod == PaymentMethod.Transfer),
            method = PaymentMethod.Transfer
        ) {
            paymentMethod = PaymentMethod.Transfer
        }
        PaymentType(
            isSelected = (paymentMethod == PaymentMethod.USSD),
            method = PaymentMethod.USSD
        ) {
            paymentMethod = PaymentMethod.USSD
        }
    }
}


@Composable
fun StepTwo() {
    Column {
        Column(
            Modifier
                .background(colorGreyLight)
                .padding(horizontal = 50.dp, vertical = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(Modifier.height(20.dp))
            Text(
                "Send to the Account No. below", style = MaterialTheme.typography.body2.copy(
                    colorGreen, fontSize = 13.sp
                )
            )

            Separator(color = colorGray, modifier = Modifier.padding(vertical = 25.dp))

            Text(
                text = "Access Bank\nMyCover.ai\n12345678904",
                style = MaterialTheme.typography.body2.copy(
                    fontSize = 20.sp,
                    color = colorNavyBlue,
                    textAlign = TextAlign.Center,
                    lineHeight = 33.sp
                )
            )

            Separator(color = colorGray, modifier = Modifier.padding(vertical = 25.dp))
        }
    }
}


