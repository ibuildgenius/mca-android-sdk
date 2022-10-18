package com.covergenius.mca_sdk_android.presentation.views.payment

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.covergenius.mca_sdk_android.common.utils.Log
import com.covergenius.mca_sdk_android.domain.model.PaymentChannel
import com.covergenius.mca_sdk_android.presentation.theme.*
import com.covergenius.mca_sdk_android.common.utils.Separator
import com.covergenius.mca_sdk_android.presentation.views.components.MyCoverButton
import com.covergenius.mca_sdk_android.presentation.views.components.MyCoverTemplate
import com.covergenius.mca_sdk_android.presentation.views.components.PaymentType

@Composable
fun PaymentScreen(onComplete: () -> Unit, viewModel: PaymentViewModel = hiltViewModel()) {

    var formStep by remember { mutableStateOf(0) }
    var buttonText by remember { mutableStateOf("Continue") }

    Log.i("form_fields", viewModel.formData.value)
    Log.i("instance_id", viewModel.businessDetails.value)

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
                        viewModel.getField("email") ?: "chuks@gmail.com",
                        style = MaterialTheme.typography.subtitle1.copy(color = colorGrey)
                    )
                    Box(modifier = Modifier.height(4.dp))
                    Row {
                        Text("Pay", style = MaterialTheme.typography.body1.copy(color = colorGrey))
                        Box(Modifier.width(4.dp))
                        Text("N${viewModel.product.value?.price}", style = MaterialTheme.typography.body2.copy(colorPrimary))
                    }
                }

                Box(modifier = Modifier.height(20.dp))

                if (formStep == 0) {
                    StepOne(viewModel)
                } else {
                    StepTwo(viewModel)
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
                    } else {
                        onComplete()
                    }
                })

            }
        }
    )
}


@Composable
fun StepOne(viewModel: PaymentViewModel) {

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
            isSelected = (viewModel.selectedPaymentMethod.value == PaymentChannel.Transfer),
            method = PaymentChannel.Transfer
        ) {
            viewModel.selectedPaymentMethod.value = PaymentChannel.Transfer
        }
        PaymentType(
            isSelected = (viewModel.selectedPaymentMethod.value == PaymentChannel.USSD),
            method = PaymentChannel.USSD
        ) {
            viewModel.selectedPaymentMethod.value = PaymentChannel.USSD
        }
    }
}


@Composable
fun StepTwo(viewModel: PaymentViewModel) {
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


