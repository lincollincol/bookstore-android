package com.linc.designsystem.extensions

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.palette.graphics.Palette

fun Palette?.getVibrantColor(defaultColor: Color = Color.Transparent): Color {
    return this?.getVibrantColor(defaultColor.toArgb())?.let { Color(it) } ?: defaultColor
}