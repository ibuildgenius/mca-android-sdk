package com.covergenius.mca_sdk_android.presentation.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(

    body1 = TextStyle(
        fontFamily = fontsMetropolis,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),

    h1 = TextStyle(
        fontFamily = fontsMetropolis,
        fontWeight = FontWeight.W700,
        fontSize = 14.sp
    ),

    h3 = TextStyle(
        fontFamily = fontsGrotesk,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),

    body2 = TextStyle(
        fontFamily = fontsGrotesk,
        fontWeight = FontWeight.W800,
        fontSize = 14.sp
    ),

    subtitle1 = TextStyle(
        fontFamily = fontsGrotesk,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp
    )

    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)