package com.linc.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme

import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import java.util.Locale

private val DarkColorPalette = darkColorScheme(
    primary = Purple200,
    primaryContainer = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColorScheme(
    primary = Purple500,
    primaryContainer = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun BookstoreTheme(
    strings: Map<String, String> = emptyMap(),
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    /*val localizedStrings = when(locale) {
        Locale("uk") -> ukrainianLocaleStrings
        Locale("pl") -> polishLocaleStrings
        Locale("sk") -> slovakLocaleStrings
        else -> englishLocaleStrings
    }*/



    CompositionLocalProvider(
        LocalStringProvider provides fromMap(strings),
        LocalIconProvider provides defaultBookstoreIcons
    ) {
        MaterialTheme(
            colorScheme = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }

}

