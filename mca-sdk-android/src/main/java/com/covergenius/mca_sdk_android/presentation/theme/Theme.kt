package com.covergenius.mca_sdk_android.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.covergenius.mca_sdk_android.R

private val DarkColorPalette = darkColors(
    primary = colorPrimary,
    primaryVariant = colorPrimaryLight,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = colorPrimaryLight,
    primaryVariant = colorPrimaryLight,
    secondary = Teal200,

    // Other default colors to override
    background = colorWhite,
    surface = colorBackground,
)

val fontsGrotesk = FontFamily(
    Font(R.font.space_grotesk_regular, FontWeight.W500),
    Font(R.font.space_grotesk_medium, weight = FontWeight.W700),
    Font(R.font.space_grotesk_bold, weight = FontWeight.W900)
)

val fontsMetropolis = FontFamily(
    Font(R.font.metropolis_regular, FontWeight.W500),
    Font(R.font.metropolis_medium, FontWeight.W700),
    Font(R.font.metropolis_bold, FontWeight.W900),
)



@Composable
fun McasdkandroidTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}