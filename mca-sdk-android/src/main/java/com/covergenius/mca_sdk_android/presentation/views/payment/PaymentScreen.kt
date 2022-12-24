package com.covergenius.mca_sdk_android.presentation.views.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.covergenius.mca_sdk_android.common.utils.Log
import com.covergenius.mca_sdk_android.domain.model.PaymentChannel
import com.covergenius.mca_sdk_android.presentation.theme.*
import com.covergenius.mca_sdk_android.common.utils.Separator
import com.covergenius.mca_sdk_android.data.cache.PAYMENT_SUCCESS_KEY
import com.covergenius.mca_sdk_android.data.cache.getFieldFromJson
import com.covergenius.mca_sdk_android.data.cache.writeBoolean
import com.covergenius.mca_sdk_android.data.cache.writeString
import com.covergenius.mca_sdk_android.data.remote.dto.getOtherFields
import com.covergenius.mca_sdk_android.presentation.views.Routes
import com.covergenius.mca_sdk_android.presentation.views.components.MyCoverButton
import com.covergenius.mca_sdk_android.presentation.views.components.MyCoverTemplate
import com.covergenius.mca_sdk_android.presentation.views.components.PaymentType

@Composable
fun PaymentScreen(
    viewModel: PaymentViewModel = hiltViewModel(),
    navigator: NavHostController,
) {

    var buttonText by remember { mutableStateOf("Continue") }

    val context = LocalContext.current

    Log.i("form_fields", viewModel.formData.value)
    Log.i("instance_id", viewModel.businessDetails.value)

    MyCoverTemplate(
        content = {

            if (viewModel.showDialog.value) {
                AlertDialog(onDismissRequest = { viewModel.showDialog.value = false },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Please Wait...", style = MaterialTheme.typography.body1)
                            Box(modifier = Modifier.width(10.dp))
                            CircularProgressIndicator(Modifier.size(30.dp))
                        }
                    },
                    buttons = {}
                )
            }

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
                        getFieldFromJson("email", viewModel.formData.value) ?: "",
                        style = MaterialTheme.typography.subtitle1.copy(color = colorGrey)
                    )
                    Box(modifier = Modifier.height(4.dp))
                    Row {
                        Text("Pay", style = MaterialTheme.typography.body1.copy(color = colorGrey))
                        Box(Modifier.width(4.dp))
                        Text(
                            "N${viewModel.product.value?.price}",
                            style = MaterialTheme.typography.body2.copy(colorPrimary)
                        )
                    }
                }

                Box(modifier = Modifier.height(20.dp))

                if (viewModel._state.value.paymentResponse?.data == null) {
                    StepOne(viewModel)
                } else {
                    buttonText = "I have sent the money"
                    StepTwo(viewModel)
                }

                Box(
                    Modifier
                        .fillMaxHeight()
                        .weight(1f)
                ) {
                    Separator(Modifier.align(Alignment.Center))
                }

                MyCoverButton(buttonText = buttonText,
                    enabled = viewModel.waitCompleted.value,
                    onPressed = {
                        if (viewModel._state.value.paymentResponse?.data == null) {
                            viewModel.initializePurchase()

                        } else {
                            if (viewModel.product.value?.formFields?.getOtherFields()
                                    ?.isNotEmpty()!!
                            ) {
                                context.writeBoolean(PAYMENT_SUCCESS_KEY, true)
                                navigator.navigate(Routes.ProductForms)
                            } else {
                                navigator.navigate(Routes.ProductList)
                            }
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

    val data = viewModel._state.value.paymentResponse?.data

    data?.let {

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
                    text = "${data.bank}\n${data.reference}\n${data.accountNumber}",
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
}


