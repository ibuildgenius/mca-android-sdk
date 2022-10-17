package com.covergenius.mca_sdk_android.presentation.views.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.covergenius.mca_sdk_android.R
import com.covergenius.mca_sdk_android.domain.model.PaymentChannel
import com.covergenius.mca_sdk_android.presentation.theme.*
import com.covergenius.mca_sdk_android.common.utils.Toolbar

@Composable
fun MyCoverTemplate(
    content: @Composable () -> Unit = {},
    onBackPressed: () -> Unit = {},
    onCanceledPressed: () -> Unit = {}
) {
    ConstraintLayout(Modifier.fillMaxSize()) {
        val (bgStart, bgBottom, layout) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.bg),
            contentDescription = "",
            modifier = Modifier.constrainAs(bgStart) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            })

        Image(
            painter = painterResource(id = R.drawable.bg),
            contentDescription = "",
            modifier = Modifier.constrainAs(bgBottom) {
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            })

        Column(
            Modifier.constrainAs(layout) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Toolbar(onBackPressed = onBackPressed, onCancelPressed = onCanceledPressed)
            Column(
                Modifier.padding(vertical = 12.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "",
                    modifier = Modifier
                        .width(160.dp)
                        .height(28.dp)
                )
            }

            Column(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                content()
            }

            Image(
                painter = painterResource(id = R.drawable.powered_by),
                contentDescription = "",
                modifier = Modifier
                    .width(160.dp)
                    .height(20.dp)
            )
        }

    }
}

@Composable
fun TitledTextField(placeholderText: String, title: String, keyboardType: KeyboardType = KeyboardType.Text) {
    Column(Modifier.padding(top = 10.dp, bottom = 15.dp)) {
        Text(title, style = MaterialTheme.typography.body1)
        Box(Modifier.height(10.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorGreyLight),
            value = "",

            onValueChange = {},
            placeholder = { Text(placeholderText, color = colorGrey) },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType)
        )
    }
}

@Composable
fun MyCoverButton(buttonText: String = "", onPressed: () -> Unit = {}) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        onClick = onPressed,

        colors = ButtonDefaults.buttonColors(backgroundColor = colorPrimary)
    ) {
        Text(
            buttonText,
            style = MaterialTheme.typography.body1.copy(color = colorWhite)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropdownField(title: String, options: List<String>) {

    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }

    Column {
        Text(text = title, style = MaterialTheme.typography.body1)
        Box(modifier = Modifier.height(10.dp))
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            TextField(
                readOnly = true,
                singleLine = true,
                value = selectedOptionText,
                textStyle = MaterialTheme.typography.body1,
                onValueChange = { },
                trailingIcon = {
                    Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "", tint = colorGrey)
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(
                    backgroundColor = colorGreyLight,
                    focusedIndicatorColor = colorGreyLight,
                    unfocusedIndicatorColor = colorGreyLight
                ),
                modifier = Modifier.fillMaxWidth()
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {

                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        onClick = {
                            selectedOptionText = selectionOption
                            expanded = false
                        }
                    ) {
                        Text(text = selectionOption)
                    }
                }

            }
        }

    }
}

@Composable
fun PaymentType(
    method: PaymentChannel = PaymentChannel.Transfer,
    isSelected: Boolean = false,
    onPressed: () -> Unit
) {
    Card(
        elevation = 0.dp,
        modifier =
        Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .background(colorGreyLight, RoundedCornerShape(8.dp))
            .border(
                BorderStroke(
                    if (isSelected) 1.5.dp else 0.5.dp,
                    if (isSelected) colorPrimary else colorGrey
                ), RoundedCornerShape(8.dp)
            )
            .clickable { onPressed() }
    ) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = if (method == PaymentChannel.Transfer) R.drawable.transfer else R.drawable.ussd),
                contentDescription = "",
                modifier = Modifier.size(42.dp)
            )

            Column(
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    if (method == PaymentChannel.Transfer) "Transfer" else "USSD",
                    style = MaterialTheme.typography.body2.copy(fontSize = 16.sp)
                )
                Box(Modifier.height(8.dp))
                Text(
                    if (method == PaymentChannel.Transfer) "Send to a bank Account" else "Select any bank to generate USSD",
                    style = MaterialTheme.typography.body2.copy(fontSize = 13.sp, color = colorGrey)
                )
            }

            Box(
                Modifier
                    .size(14.dp)
                    .border(
                        BorderStroke(0.5.dp, if (isSelected) colorPrimary else colorGrey),
                        CircleShape
                    )
                    .background(if (isSelected) colorPrimary else colorWhite, shape = CircleShape)

            ) {
                if (isSelected) {
                    Box(
                        Modifier
                            .size(6.dp)
                            .background(colorWhite, shape = CircleShape)
                            .align(Alignment.Center)

                    )
                }
            }


        }
    }
}