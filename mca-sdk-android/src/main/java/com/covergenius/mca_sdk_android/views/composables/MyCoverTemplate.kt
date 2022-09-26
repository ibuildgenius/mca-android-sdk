package com.covergenius.mca_sdk_android.views.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.covergenius.mca_sdk_android.R
import com.covergenius.mca_sdk_android.ui.theme.*
import com.covergenius.mca_sdk_android.utils.Toolbar

@Composable
fun MyCoverTemplate(
    content: @Composable () -> Unit = {},
    onBackPressed: () -> Unit = {},
    onCancledPressed: () -> Unit = {}
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
            Toolbar(onBackPressed = onBackPressed, onCancelPressed = onCancledPressed)
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
fun TitledTextField(placeholderText: String, title: String) {
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
            singleLine = true
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

    Column() {
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