package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val VihaanLightColorScheme = lightColorScheme(
    primary = VihaanDeepNavy,
    onPrimary = VihaanWhite,
    primaryContainer = VihaanRoyalBlue,
    onPrimaryContainer = VihaanWhite,
    secondary = VihaanGold,
    onSecondary = VihaanDeepNavy,
    secondaryContainer = VihaanLightGold,
    onSecondaryContainer = VihaanDeepNavy,
    tertiary = VihaanRoyalBlue,
    onTertiary = VihaanWhite,
    background = VihaanOffWhite,
    onBackground = VihaanDarkText,
    surface = VihaanWhite,
    onSurface = VihaanDarkText,
    surfaceVariant = VihaanSurfaceLight,
    onSurfaceVariant = VihaanSecondaryText,
    outline = VihaanBorder,
    error = VihaanError,
    onError = VihaanWhite
)

private val VihaanDarkColorScheme = darkColorScheme(
    primary = VihaanGold,
    onPrimary = VihaanDeepNavy,
    primaryContainer = VihaanRoyalBlue,
    onPrimaryContainer = VihaanWhite,
    secondary = VihaanLightGold,
    onSecondary = VihaanDeepNavy,
    tertiary = VihaanRoyalBlue,
    onTertiary = VihaanWhite,
    background = VihaanDeepNavy,
    onBackground = VihaanWhite,
    surface = Color(0xFF0D2342),
    onSurface = VihaanWhite,
    outline = Color(0xFF1E3A63),
    error = VihaanError,
    onError = VihaanWhite
)

@Composable
fun VihaanTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) VihaanDarkColorScheme else VihaanLightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
