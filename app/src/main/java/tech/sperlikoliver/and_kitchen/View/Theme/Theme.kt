package tech.sperlikoliver.and_kitchen.View.Theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color(0xFF2196f3),
    primaryVariant = Color(0xFFffffff),
    background = Color(0xFF000000),
    secondary = Color(0xFFd32f2f),
    secondaryVariant = Color(0xFF8bc34a),
    error = Color(0xFFd500000)
)

private val LightColorPalette = lightColors(
    primary = Color(0xFFf57c00),
    primaryVariant = Color(0xFF000000),
    background = Color(0xFFffffff),
    secondary = Color(0xFFd32f2f),
    secondaryVariant = Color(0xFF8bc34a),
    error = Color(0xFFd500000)
)

@Composable
fun And_kitchenTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        content = content
    )
}