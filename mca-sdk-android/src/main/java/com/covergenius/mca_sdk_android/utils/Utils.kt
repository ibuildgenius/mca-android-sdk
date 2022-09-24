package com.covergenius.mca_sdk_android.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.covergenius.mca_sdk_android.ui.theme.colorPrimary

@Composable
fun Toolbar(onBackPressed: () -> Unit, onCancelPressed: () -> Unit) {
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


@Preview
@Composable
fun ShowToolbar() {
    Toolbar(onBackPressed = {}, onCancelPressed = {})
}