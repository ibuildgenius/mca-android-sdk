@file:OptIn(ExperimentalTime::class)

package com.covergenius.mca_sdk_android.presentation.views.payment

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.covergenius.mca_sdk_android.common.utils.Log
import com.covergenius.mca_sdk_android.domain.model.PaymentChannel
import com.covergenius.mca_sdk_android.presentation.theme.*
import com.covergenius.mca_sdk_android.common.utils.Separator
import com.covergenius.mca_sdk_android.data.cache.*
import com.covergenius.mca_sdk_android.data.remote.dto.getOtherFields
import com.covergenius.mca_sdk_android.presentation.views.Routes
import com.covergenius.mca_sdk_android.presentation.views.components.*
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

@SuppressLint("UnrememberedMutableState")
@Composable
fun PaymentScreen(
    viewModel: PaymentViewModel = hiltViewModel(),
    navigator: NavHostController,
) {

    var buttonText by remember { mutableStateOf("Continue") }

    var transactionTimerProgress by remember { mutableStateOf(1.0) }
    var timeleft by remember { mutableStateOf(10.0) }
    var startTimer by remember { mutableStateOf(false) }
    val showCancelDialog: Boolean by viewModel.showCancelDialog.collectAsState()

    val context = LocalContext.current

    Log.i("form_fields", viewModel.formData.value)
    Log.i("instance_id", viewModel.businessDetails.value)

    DisplayDialog(
        show = showCancelDialog,
        onDialogDismiss = viewModel::onDialogDismiss,
        onDialogConfirm = {
            viewModel.onDialogConfirm(); navigator.navigate(Routes.ProductList) {
            popUpTo(Routes.ProductList) {
                inclusive = true
            }
        }
        }
    )


    MyCoverTemplate(
        onCanceledPressed = { viewModel.onOpenDialogClicked() },
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

            /*   if(viewModel._state.value.error.isNotEmpty()) {
                   //Notifier.showNotification(context, title = "Error", text = viewModel._state.value.error, backgroundColor = colorError)
                   Popup(alignment = Alignment.TopCenter, properties = PopupProperties(dismissOnClickOutside = true, focusable = true)) {
                       Box(Modifier.padding(12.dp)) {
                           Column(Modifier.padding(11.dp).background(colorError)) {
                               Text(viewModel._state.value.error, style = MaterialTheme.typography.body1, color = Color.White)
                           }
                       }
                   }
               }*/



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
                            if(viewModel._state.value.response?.data != null)  getFieldFromJson("email", viewModel.formData.value) else viewModel.product.value!!.prefix,
                            style = MaterialTheme.typography.subtitle1.copy(color = colorGrey)
                        )
                        Box(modifier = Modifier.height(4.dp))

                        Row {
                            if(viewModel._state.value.response?.data != null) {
                                Text(
                                    "Pay",
                                    style = MaterialTheme.typography.body1.copy(color = colorGrey)
                                )

                                Box(Modifier.width(4.dp))
                            }

                            Text(
                                if(viewModel._state.value.response?.data != null) "N${viewModel._state.value.response!!.data.amount}" else viewModel.product.value!!.name,
                                style = MaterialTheme.typography.body2.copy(colorPrimary)
                            )
                        }
                    }


                Box(modifier = Modifier.height(20.dp))

                if (viewModel._state.value.response?.data == null) {
                    StepOne(viewModel)
                } else {
                    startTimer = true
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


                if (viewModel._state.value.response?.data != null && !viewModel.waitCompleted.value) {

                    LaunchedEffect(key1 = transactionTimerProgress, key2 = startTimer) {
                        if (transactionTimerProgress > 0 && startTimer) {
                            delay(30.seconds)
                            transactionTimerProgress -= 0.05
                            timeleft -= 0.5
                            if (!viewModel.transLoading.value && transactionTimerProgress > 0) {
                                viewModel.verifyTransaction()
                            }
                        }
                    }

                    Column(Modifier.padding(vertical = 8.dp)) {
                        Text(
                            "Transaction would expire in ${timeleft.toInt()} minute(s)",
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                        LinearProgressIndicator(progress = transactionTimerProgress.toFloat(), modifier = Modifier
                            .height(4.dp).fillMaxWidth())
                    }
                }
                MyCoverButton(buttonText = buttonText,
                    enabled = viewModel.waitCompleted.value,
                    onPressed = {
                        if (viewModel._state.value.response?.data == null) {
                            viewModel.initializePurchase()

                        } else {
                            if (viewModel.product.value?.formFields?.getOtherFields()
                                    ?.isNotEmpty()!!
                            ) {
                                context.writeBoolean(PAYMENT_SUCCESS_KEY, true)
                                context.writeString(TRANSACTION_REF_KEY, viewModel._state.value.response!!.data.reference)
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
    val data = viewModel._state.value.response?.data

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
