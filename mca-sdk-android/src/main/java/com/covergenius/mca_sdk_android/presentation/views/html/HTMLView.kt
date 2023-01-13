package com.covergenius.mca_sdk_android.presentation.views.html

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.core.content.res.ResourcesCompat
import com.covergenius.mca_sdk_android.R
import com.covergenius.mca_sdk_android.presentation.theme.Typography

@Composable
fun HtmlText(
    modifier: Modifier = Modifier,
    html: String,
    textStyle: TextStyle = Typography.body1,
) {

    val context = LocalContext.current

    val fontResId = when(textStyle.fontWeight) {
        FontWeight.Medium -> R.font.metropolis_medium
        else -> R.font.metropolis_regular
    }

    val font = ResourcesCompat.getFont(context,fontResId)

}