package com.covergenius.mca_sdk_android.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.covergenius.mca_sdk_android.presentation.theme.colorGreyLight
import com.covergenius.mca_sdk_android.presentation.theme.colorPrimary

@Composable
fun Toolbar(margin: Dp = 0.dp, onBackPressed: () -> Unit, onCancelPressed: () -> Unit) {
    Column(Modifier.padding(margin)) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = onBackPressed) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Previous step",
                    tint = colorPrimary
                )
            }
            IconButton(onClick = onBackPressed) {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = "Cancel Process",
                    tint = colorPrimary
                )
            }
        }
    }
}

@Composable
fun Separator(modifier: Modifier = Modifier, color: Color = colorGreyLight) {
    Column(modifier) {
        Box(modifier = Modifier
            .height(1.dp)
            .background(color)
            .fillMaxWidth())
    }
}

@Preview
@Composable
fun ShowToolbar() {
    Toolbar(onBackPressed = {}, onCancelPressed = {})
}


fun TextStyle.center() : TextStyle =
    this.copy(textAlign = TextAlign.Center)
