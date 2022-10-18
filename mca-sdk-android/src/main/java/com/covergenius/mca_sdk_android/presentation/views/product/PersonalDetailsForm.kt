package com.covergenius.mca_sdk_android.presentation.views.product

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import com.covergenius.mca_sdk_android.R
import com.covergenius.mca_sdk_android.common.utils.Log
import com.covergenius.mca_sdk_android.data.remote.dto.*
import com.covergenius.mca_sdk_android.presentation.theme.*
import com.covergenius.mca_sdk_android.presentation.views.components.*
import java.util.*

@Composable
fun ProductDetailsForm(
    onContinuePressed: () -> Unit,
    viewModel: ProductDetailViewModel = hiltViewModel()
) {
    val hintText by remember { mutableStateOf("Enter details as it appear on legal documents") }

    val animationTime = 200 // milliseconds
    val animationTimeExit = 0 // milliseconds

    val product = viewModel.product.value

    //gets 3 fields only
    val fields = product?.formFields?.getPriorityFields()?.chunked(3)

    MyCoverTemplate(
        onBackPressed = {
            if (viewModel.formIndex.value > 0) {
                viewModel.formIndex.value -= 1
            }
        },
        content = {
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
                                    fontSize = 12.sp
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
                            if (viewModel.formIndex.value < fields?.size!! - 1) {
                                viewModel.formIndex.value += 1
                            } else {
                                viewModel.saveFieldEntries()
                                onContinuePressed()
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
            } else {
                TitledTextField(
                    placeholderText = formField.description,
                    title = formField.label,
                    keyboardType = formField.getKeyboardType(),
                    onValueChange = { value ->
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

    val year: Int
    val month: Int
    val day: Int

    val calendar = Calendar.getInstance()

    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val date = remember { mutableStateOf("") }

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            date.value = "$dayOfMonth-$month-$year"

            viewModel.addFormDataEntry(formField.name, date.value)
        }, year, month, day
    )


    TitledTextField(
        placeholderText = formField.description,
        title = formField.label,
        readOnly = true,
        value = date.value,
        onPressed = { Log.d("", "Xheghun David"); datePickerDialog.show() },
        trailingIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_calendar_today),
                contentDescription = ""
            )
        }
    )

}