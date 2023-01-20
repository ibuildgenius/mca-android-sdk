package com.covergenius.mca_sdk_android.presentation.views.html

import android.view.Gravity
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import com.covergenius.mca_sdk_android.R
import com.covergenius.mca_sdk_android.presentation.theme.Typography

@Composable
fun HtmlText(
    modifier: Modifier = Modifier,
    html: String,
    textStyle: TextStyle = Typography.body1,
) {
    AndroidView(
        modifier = modifier,
        factory = { context -> TextView(context) },
        update = { it.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT) }
    )
}