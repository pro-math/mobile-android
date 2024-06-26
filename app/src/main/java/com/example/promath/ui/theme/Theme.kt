package com.example.promath.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.domain.models.ResultModel
import com.example.domain.usecase.GetCurrentThemeUseCase
import com.example.promath.ui.themenew.palette
import com.example.promath.ui.themenew.paletteCupcake
import com.example.promath.ui.themenew.paletteDark
import com.example.promath.ui.themenew.paletteEmerald
import com.example.promath.ui.themenew.paletteValentine
import org.koin.android.ext.android.inject

private val LightColorScheme = darkColorScheme(
    background = Background,
    primary = Primary,
    onPrimary = PrimaryText,
    secondary = Secondary,
    onSecondary = SecondaryText,
    surface = Surface
)

private val DarkColorScheme = darkColorScheme(
    background = Background,
    primary = Primary,
    onPrimary = PrimaryText,
    secondary = Secondary,
    onSecondary = SecondaryText,
    surface = Surface
)

@Composable
fun ProMathTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    getCurrentThemeUseCase: GetCurrentThemeUseCase,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = palette.base100.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }
    val theme = getCurrentThemeUseCase.execute()
    if (theme.status == ResultModel.Status.SUCCESS) {
        palette = when (theme.data) {
            "dark" -> paletteDark
            "cupcake" -> paletteCupcake
            "valentine" -> paletteValentine
            "emerald" -> paletteEmerald
            else -> paletteDark
        }
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}