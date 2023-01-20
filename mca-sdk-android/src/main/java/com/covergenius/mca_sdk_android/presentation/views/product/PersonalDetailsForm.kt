package com.covergenius.mca_sdk_android.presentation.views.product

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.CountDownTimer
import android.widget.DatePicker
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.covergenius.mca_sdk_android.R
import com.covergenius.mca_sdk_android.common.Constants
import com.covergenius.mca_sdk_android.data.cache.PAYMENT_SUCCESS_KEY
import com.covergenius.mca_sdk_android.data.cache.getBoolean
import com.covergenius.mca_sdk_android.data.remote.dto.*
import com.covergenius.mca_sdk_android.presentation.theme.*
import com.covergenius.mca_sdk_android.presentation.views.Routes
import com.covergenius.mca_sdk_android.presentation.views.components.*
import java.util.*


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ProductDetailsForm(
    onContinuePressed: () -> Unit,
    navigator: NavController,
    viewModel: ProductDetailViewModel = hiltViewModel()
) {
    val hintText by remember { mutableStateOf("Enter details as it appear on legal documents") }

    val animationTime = 200 // milliseconds
    val animationTimeExit = 0 // milliseconds

    val product = viewModel.product.value

    val context = LocalContext.current

    val hasPaid = context.getBoolean(PAYMENT_SUCCESS_KEY)

    var showDialog by remember { mutableStateOf(false) }

    val showCancelDialog: Boolean by viewModel.showCancelDialog.collectAsState()

    val timer = object : CountDownTimer(5000, 1000) {
        override fun onTick(p0: Long) {
        }

        override fun onFinish() {
            showDialog = false
            navigator.navigate(Routes.ProductList) {
                popUpTo(Routes.ProductList) {
                    inclusive = true
                }
            }
        }
    }

    //split fields into a group of 3s
    val fields = if (hasPaid) product?.formFields?.getOtherFields()
        ?.chunked(3) else product?.formFields?.getPriorityFields()?.chunked(3)



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
        onBackPressed = {
            if (viewModel.formIndex.value > 0) {
                viewModel.formIndex.value -= 1
            }
        },
        onCanceledPressed = { viewModel.onOpenDialogClicked() },

        content = {

            if (showDialog) {
                AlertDialog(onDismissRequest = { showDialog = false },
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

            Column() {
                if (product != null) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .background(colorPrimaryBg)
                                .padding(12.dp), verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Filled.Info,
                                contentDescription = "",
                                tint = colorGreen,
                                modifier = Modifier.size(12.dp)
                            )
                            Box(modifier = Modifier.width(10.dp))
                            Text(
                                text = hintText,
                                style = MaterialTheme.typography.body1.copy(fontSize = 12.sp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Text(
                                "Learn more",
                                style = MaterialTheme.typography.h1.copy(
                                    color = colorAccent,
                                    fontSize = 10.sp,
                                    fontStyle = FontStyle.Italic
                                )
                            )
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            )
                            Text(
                                "Underwritten by ",
                                style = MaterialTheme.typography.h1.copy(
                                    fontSize = 12.sp,
                                    color = colorGrey
                                )
                            )
                            Text(
                                product.name.uppercase(),
                                style = MaterialTheme.typography.h1.copy(
                                    colorSpaceGray,
                                    fontSize = 10.sp
                                )
                            )
                            Box(Modifier.width(4.dp))
                            Image(
                                painter = painterResource(id = R.drawable.aiico),
                                contentDescription = "",
                                modifier = Modifier
                                    .width(40.dp)
                                    .height(12.dp)
                            )
                        }

                        Box(Modifier.height(8.dp))

                        fields?.forEachIndexed { index, list ->
                            AnimatedVisibility(
                                modifier = Modifier.fillMaxWidth(),
                                visible = index == viewModel.formIndex.value,
                                enter = slideInHorizontally(
                                    initialOffsetX = { -300 },
                                    animationSpec = tween(
                                        durationMillis = animationTime,
                                        easing = LinearEasing
                                    )
                                ),
                                exit = slideOutHorizontally(
                                    targetOffsetX = { -300 },
                                    animationSpec = tween(
                                        durationMillis = animationTimeExit,
                                        easing = LinearEasing
                                    )
                                )
                            ) {
                                FormOne(list, viewModel)
                            }
                        }

                        MyCoverButton("Continue", onPressed = {

                            if (hasPaid) {
                                showDialog = true
                                timer.start()
                            } else {
                                if (viewModel.formIndex.value < fields?.size!! - 1) {
                                    viewModel.formIndex.value += 1
                                } else {
                                    viewModel.addFormDataEntry("product_id", product.id)
                                    viewModel.saveFieldEntries()
                                    onContinuePressed()
                                }
                            }
                        })
                    }
                }
            }
        })
}

@Composable
fun FormOne(fields: List<FormField>, viewModel: ProductDetailViewModel) {
    LazyColumn {
        items(fields.size) {
            val formField = fields[it]

            if (formField.inputType.lowercase() == "date") {
                DateField(formField = formField, viewModel)
            } else if (formField.formField.name.lowercase() == "select") {

                if (!viewModel.select.value.containsKey(formField.name)) {
                    viewModel.getSelect(
                        formField.name,
                        "${Constants.BASE_URL}/v1${formField.dataUrl}"
                    )
                    return@items
                }

                SelectDropdown(formField = formField, viewModel = viewModel)
            } else if (formField.inputType.lowercase() == "file") {
                filePicker(formField = formField, viewModel = viewModel)
            } else {

                val data = remember { mutableStateOf("") }

                TitledTextField(
                    placeholderText = formField.description,
                    title = formField.label,
                    keyboardType = formField.getKeyboardType(),
                    value = data.value,
                    onValueChange = { value ->
                        data.value = value
                        viewModel.addFormDataEntry(formField.name, value)
                    }
                )
            }
        }
    }
}

@Composable
fun DateField(formField: FormField, viewModel: ProductDetailViewModel) {
    val context = LocalContext.current

    val mYear: Int
    val mMonth: Int
    val mDay: Int

    val calendar = Calendar.getInstance()

    mYear = calendar.get(Calendar.YEAR)
    mMonth = calendar.get(Calendar.MONTH)
    mDay = calendar.get(Calendar.DAY_OF_MONTH)

    calendar.time = Date()

    val date = remember { mutableStateOf("") }
    val isoDate = remember { mutableStateOf("") }

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            date.value = "$dayOfMonth-$month-$year"
            isoDate.value = "$dayOfMonth$month$year"
            viewModel.addFormDataEntry(formField.name, isoDate.value)
        }, mYear, mMonth, mDay
    )

    TitledTextField(
        placeholderText = formField.description,
        title = formField.label,
        readOnly = true,
        enabled = false,
        value = date.value,
        onPressed = { datePickerDialog.show() },
        trailingIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_calendar_today),
                contentDescription = ""
            )
        }
    )

}

@Composable
fun SelectDropdown(formField: FormField, viewModel: ProductDetailViewModel) {
    DropdownField(
        title = formField.label,
        options = viewModel.select.value[formField.name] ?: listOf()
    )
}

@Composable
fun filePicker(formField: FormField, viewModel: ProductDetailViewModel) {

}