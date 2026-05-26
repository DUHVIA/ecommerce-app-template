package com.example.ecommerce_template.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val IronCoreDarkColorScheme = darkColorScheme(
    primary = PrimaryNeon,
    onPrimary = NeutralBlack,
    secondary = SecondaryDark,
    onSecondary = TextWhite,
    tertiary = TertiaryTeal,
    background = NeutralBlack,
    onBackground = TextWhite,
    surface = NeutralBlack,
    onSurface = TextWhite,
    surfaceVariant = SurfaceCard,
    onSurfaceVariant = TextGray
)

@Composable
fun IronCoreTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = IronCoreDarkColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}