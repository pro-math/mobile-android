package com.example.promath.ui.themenew

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val palette = Colors(
    primary = Color(0xFF7582ff),
    primaryContent = Color(0xFF050617),
    secondary = Color(0xFFff71cf),
    secondaryContent = Color(0xFF190211),
    accent = Color(0xFF00c7b5),
    accentContent = Color(0xFF000F0C),
    neutral = Color(0xFF2a323c),
    neutralContent = Color(0xFFA6ADBB),
    base100 = Color(0xFF1d232a),
    base200 = Color(0xFF191e24),
    base300 = Color(0xFF15191e),
    baseContent = Color(0xFFA6ADBB),
    baseContentSecondary = Color(0xFF6C7079),
    info = Color(0xFF8be9fd),
    infoContent = Color(0xFF000000),
    success = Color(0xFF50fa7b),
    successContent = Color(0xFF000000),
    warning = Color(0xFFf1fa8c),
    warningContent = Color(0xFF000000),
    error = Color(0xFFff5555),
    errorContent = Color(0xFF000000)
)

val LocalColorProvider = staticCompositionLocalOf<Colors> { error("No default implementation") }

@Composable
fun DarkTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        value = LocalColorProvider provides palette,
        content = content
    )
}