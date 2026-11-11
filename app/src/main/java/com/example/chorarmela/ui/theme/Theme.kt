package com.example.chorarmela.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = KidsSkyBlue,
    onPrimary = Color.White,
    primaryContainer = KidsSkyBlueLight,
    onPrimaryContainer = KidsSkyBlueDark,
    secondary = KidsMangoOrange,
    onSecondary = Color.White,
    secondaryContainer = KidsMangoLight,
    onSecondaryContainer = Color(0xFFE65100),
    tertiary = KidsCoralPink,
    onTertiary = Color.White,
    tertiaryContainer = KidsCoralLight,
    onTertiaryContainer = Color(0xFFB71C1C),
    background = BackgroundLight,
    onBackground = TextPrimary,
    surface = SurfaceLight,
    onSurface = TextPrimary,
    surfaceVariant = Color(0xFFECEFF1),
    onSurfaceVariant = TextSecondary
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF4FC3F7),
    onPrimary = Color(0xFF01579B),
    primaryContainer = Color(0xFF0277BD),
    onPrimaryContainer = Color(0xFFE1F5FE),
    secondary = Color(0xFFFFB74D),
    onSecondary = Color(0xFFE65100),
    secondaryContainer = Color(0xFFF57C00),
    onSecondaryContainer = Color(0xFFFFF3E0),
    tertiary = Color(0xFFFF8A80),
    onTertiary = Color(0xFFB71C1C),
    background = Color(0xFF121212),
    onBackground = Color(0xFFE0E0E0),
    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFE0E0E0)
)

@Composable
fun ChorarMelaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
