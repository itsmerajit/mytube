package com.mytube.app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat

// Comprehensive Color Palette
object MyTubeThemeColors {
    // Primary Color Palette
    val Primary = Color(0xFF6200EE)
    val PrimaryVariant = Color(0xFF3700B3)
    val PrimaryContainer = Color(0xFF9256E3)
    
    // Secondary Color Palette
    val Secondary = Color(0xFF03DAC6)
    val SecondaryVariant = Color(0xFF018786)
    val SecondaryContainer = Color(0xFF45C8BC)
    
    // Background and Surface Colors
    val Background = Color(0xFFF5F5F5)
    val Surface = Color(0xFFFFFFFF)
    val SurfaceVariant = Color(0xFFE0E0E0)
    
    // Text and Icon Colors
    val OnPrimary = Color(0xFFFFFFFF)
    val OnSecondary = Color(0xFF000000)
    val OnBackground = Color(0xFF000000)
    val OnSurface = Color(0xFF121212)
    
    // Error and Accent Colors
    val Error = Color(0xFFB00020)
    val OnError = Color(0xFFFFFFFF)
    
    // Dark Theme Variants
    val DarkBackground = Color(0xFF121212)
    val DarkSurface = Color(0xFF1E1E1E)
    val DarkOnSurface = Color(0xFFE0E0E0)
}

// Custom Typography
val MyTubeTypography = Typography(
    displayLarge = TextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    ),
    displayMedium = TextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 45.sp,
        lineHeight = 52.sp
    ),
    displaySmall = TextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 36.sp,
        lineHeight = 44.sp
    ),
    headlineLarge = TextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 32.sp,
        lineHeight = 40.sp
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 28.sp,
        lineHeight = 36.sp
    ),
    headlineSmall = TextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 24.sp,
        lineHeight = 32.sp
    )
)

// Light Color Scheme
private val LightColorScheme = lightColorScheme(
    primary = MyTubeThemeColors.Primary,
    onPrimary = MyTubeThemeColors.OnPrimary,
    primaryContainer = MyTubeThemeColors.PrimaryContainer,
    secondary = MyTubeThemeColors.Secondary,
    onSecondary = MyTubeThemeColors.OnSecondary,
    secondaryContainer = MyTubeThemeColors.SecondaryContainer,
    background = MyTubeThemeColors.Background,
    onBackground = MyTubeThemeColors.OnBackground,
    surface = MyTubeThemeColors.Surface,
    onSurface = MyTubeThemeColors.OnSurface,
    surfaceVariant = MyTubeThemeColors.SurfaceVariant,
    error = MyTubeThemeColors.Error,
    onError = MyTubeThemeColors.OnError
)

// Dark Color Scheme
private val DarkColorScheme = darkColorScheme(
    primary = MyTubeThemeColors.Primary,
    onPrimary = MyTubeThemeColors.OnPrimary,
    primaryContainer = MyTubeThemeColors.PrimaryVariant,
    secondary = MyTubeThemeColors.Secondary,
    onSecondary = MyTubeThemeColors.OnSecondary,
    secondaryContainer = MyTubeThemeColors.SecondaryVariant,
    background = MyTubeThemeColors.DarkBackground,
    onBackground = MyTubeThemeColors.DarkOnSurface,
    surface = MyTubeThemeColors.DarkSurface,
    onSurface = MyTubeThemeColors.DarkOnSurface,
    error = MyTubeThemeColors.Error,
    onError = MyTubeThemeColors.OnError
)

@Composable
fun MyTubeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MyTubeTypography,
        content = content
    )
}

// Utility function to get theme colors
fun getThemeColors(isDarkTheme: Boolean): ColorScheme {
    return if (isDarkTheme) DarkColorScheme else LightColorScheme
}
