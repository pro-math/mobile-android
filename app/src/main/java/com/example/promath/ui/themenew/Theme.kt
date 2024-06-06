package com.example.promath.ui.themenew

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val paletteDark = Colors(
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

val paletteCupcake = Colors(
    primary = Color(0xFF65C3C8),
    primaryContent = Color(0xFF030E0F),
    secondary = Color(0xFFEF9FBC),
    secondaryContent = Color(0xFF14090D),
    accent = Color(0xFFEEAF3A),
    accentContent = Color(0xFF140B01),
    neutral = Color(0xFF291334),
    neutralContent = Color(0xFFD0CAD3),
    base100 = Color(0xFFFAF7F5),
    base200 = Color(0xFFEFEAE6),
    base300 = Color(0xFFE7E2DF),
    baseContent = Color(0xFF291334),
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

val paletteValentine = Colors(
    primary = Color(0xFFE96D7B),
    primaryContent = Color(0xFF130405),
    secondary = Color(0xFFA991F7),
    secondaryContent = Color(0xFF0A0715),
    accent = Color(0xFF66B1B3),
    accentContent = Color(0xFF040C0C),
    neutral = Color(0xFFAF4670),
    neutralContent = Color(0xFFF0D6E8),
    base100 = Color(0xFFFAE7F4),
    base200 = Color(0xFFE3D2DE),
    base300 = Color(0xFFCDBDC8),
    baseContent = Color(0xFF632C3B),
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

val paletteEmerald = Colors(
    primary = Color(0xFF66CC8A),
    primaryContent = Color(0xFF223D30),
    secondary = Color(0xFF377CFB),
    secondaryContent = Color(0xFFFFFFFF),
    accent = Color(0xFFF68067),
    accentContent = Color(0xFF000000),
    neutral = Color(0xFF333C4D),
    neutralContent = Color(0xFFF9FAFB),
    base100 = Color(0xFFFFFFFF),
    base200 = Color(0xFFE8E8E8),
    base300 = Color(0xFFD1D1D1),
    baseContent = Color(0xFF333C4D),
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

var palette = paletteDark

val LocalColorProvider = staticCompositionLocalOf<Colors> { error("No default implementation") }

@Composable
fun DarkTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        value = LocalColorProvider provides palette,
        content = content
    )
}