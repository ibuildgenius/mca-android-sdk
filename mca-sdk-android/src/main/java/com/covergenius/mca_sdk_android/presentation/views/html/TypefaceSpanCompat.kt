package com.covergenius.mca_sdk_android.presentation.views.html

import android.annotation.TargetApi
import android.graphics.Typeface
import android.os.Build
import android.text.TextPaint
import android.text.style.MetricAffectingSpan
import androidx.compose.ui.text.android.InternalPlatformTextApi
import androidx.compose.ui.text.android.style.TypefaceSpan

fun Typeface.getTypefaceSpan(): MetricAffectingSpan =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        typefaceSpanCompatV28(this)
    } else {
        CustomTypefaceSpan(this)
    }

@OptIn(InternalPlatformTextApi::class)
@TargetApi(Build.VERSION_CODES.P)
private fun typefaceSpanCompatV28(typeface: Typeface) = TypefaceSpan(typeface)

private class CustomTypefaceSpan(private val typeface: Typeface?) : MetricAffectingSpan() {

    override fun updateDrawState(paint: TextPaint) {
        paint.typeface = typeface
    }

    override fun updateMeasureState(paint: TextPaint) {
        paint.typeface = typeface
    }
}