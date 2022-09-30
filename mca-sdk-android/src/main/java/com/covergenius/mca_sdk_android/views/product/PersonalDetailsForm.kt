package com.covergenius.mca_sdk_android.views.product

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.covergenius.mca_sdk_android.R
import com.covergenius.mca_sdk_android.model.Product
import com.covergenius.mca_sdk_android.views.theme.*
import com.covergenius.mca_sdk_android.views.composables.DropdownField
import com.covergenius.mca_sdk_android.views.composables.MyCoverButton
import com.covergenius.mca_sdk_android.views.composables.MyCoverTemplate
import com.covergenius.mca_sdk_android.views.composables.TitledTextField


@Composable
fun ProductDetailsForm(product: Product, onContinuePressed: () -> Unit) {
    var formProgress by remember { mutableStateOf(0) }
    var hintText by remember { mutableStateOf("Enter details as it appear on legal documents") }

    val animationTime = 200 // milliseconds
    val animationTimeExit = 0 // milliseconds


    MyCoverTemplate(content = {
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
                    style = MaterialTheme.typography.h1.copy(fontSize = 12.sp, color = colorGrey)
                )
                Text(
                    "AIICO",
                    style = MaterialTheme.typography.h1.copy(colorSpaceGray, fontSize = 12.sp)
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
            AnimatedVisibility(

                modifier = Modifier.fillMaxWidth(),
                visible = formProgress == 0,
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
                FormOne()
            }

            AnimatedVisibility(
                modifier = Modifier.fillMaxWidth(),
                visible = formProgress == 1,
                enter = slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(
                        durationMillis = animationTime,
                        easing = LinearEasing
                    )
                ),
                exit = slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(
                        durationMillis = animationTimeExit,
                        easing = LinearEasing
                    )
                )
            ) {
                FormTwo()
            }

            MyCoverButton("Continue", onPressed = {
                if (formProgress == 0) {
                    formProgress = 1
                    hintText = "Enter Vehicle details"
                } else {
                    onContinuePressed()
                }
            })

        }
    })
}


@Composable
fun FormOne() {
    Column {
        TitledTextField(
            placeholderText = "First Name, Last Name",
            title = "Name of Plan Owner"
        )
        TitledTextField(placeholderText = "Enter email address", title = "Email")
        TitledTextField(placeholderText = "Enter phone number", title = "Phone")


    }
}

@Composable
fun FormTwo() {
    Column {
        Row {
            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                DropdownField(
                    title = "Vehicle Type",
                    options = listOf("Commercial", "Personal")
                )
            }
            Box(Modifier.width(10.dp))
            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                DropdownField(
                    title = "Kind of Vehicle",
                    options = listOf("Car", "Bus", "Trailer", "Motorcycle").sorted()
                )
            }
        }
        TitledTextField(placeholderText = "Enter Vehicle Plate Number", title = "Vehicle Plate No.")
    }
}