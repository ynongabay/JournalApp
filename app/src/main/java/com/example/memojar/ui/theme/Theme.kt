package com.example.memojar.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// ============================================================
// COLOR DEFINITIONS
// ============================================================
// Material 3 uses a system of color "roles". Each role has a
// specific purpose in the UI (e.g., "primary" for main actions,
// "secondary" for less prominent elements, "error" for errors).
//
// Each color has an "on" variant — the text/icon color that
// should appear ON TOP of that color for good contrast.
//
// These warm amber/brown tones give the app a notebook-like feel.
// ============================================================

// --- Light Theme Colors ---
val Md_theme_light_primary = Color(0xFF8B5000)             // Main accent color (amber brown)
val Md_theme_light_onPrimary = Color(0xFFFFFFFF)           // White text on primary
val Md_theme_light_primaryContainer = Color(0xFFFFDCBE)    // Lighter primary for backgrounds
val Md_theme_light_onPrimaryContainer = Color(0xFF2C1600)  // Dark text on primary container
val Md_theme_light_secondary = Color(0xFF745B45)
val Md_theme_light_onSecondary = Color(0xFFFFFFFF)
val Md_theme_light_secondaryContainer = Color(0xFFFFDCBE)
val Md_theme_light_onSecondaryContainer = Color(0xFF281808)
val Md_theme_light_tertiary = Color(0xFF5A6237)
val Md_theme_light_onTertiary = Color(0xFFFFFFFF)
val Md_theme_light_tertiaryContainer = Color(0xFFDFE8B1)
val Md_theme_light_onTertiaryContainer = Color(0xFF181E00)
val Md_theme_light_error = Color(0xFFBA1A1A)               // Red for errors
val Md_theme_light_errorContainer = Color(0xFFFFDAD6)
val Md_theme_light_onError = Color(0xFFFFFFFF)
val Md_theme_light_onErrorContainer = Color(0xFF410002)
val Md_theme_light_background = Color(0xFFFFFBFF)          // Paper white background
val Md_theme_light_onBackground = Color(0xFF201A16)
val Md_theme_light_surface = Color(0xFFFFFBFF)
val Md_theme_light_onSurface = Color(0xFF201A16)
val Md_theme_light_surfaceVariant = Color(0xFFF3DFD1)
val Md_theme_light_onSurfaceVariant = Color(0xFF51443B)

// --- Dark Theme Colors ---
val Md_theme_dark_primary = Color(0xFFFFB870)
val Md_theme_dark_onPrimary = Color(0xFF4A2800)
val Md_theme_dark_primaryContainer = Color(0xFF6A3B00)
val Md_theme_dark_onPrimaryContainer = Color(0xFFFFDCBE)
val Md_theme_dark_secondary = Color(0xFFE3C0A4)
val Md_theme_dark_onSecondary = Color(0xFF422C1A)
val Md_theme_dark_secondaryContainer = Color(0xFF5A432F)
val Md_theme_dark_onSecondaryContainer = Color(0xFFFFDCBE)
val Md_theme_dark_tertiary = Color(0xFFC3CB97)
val Md_theme_dark_onTertiary = Color(0xFF2C330D)
val Md_theme_dark_tertiaryContainer = Color(0xFF424A21)
val Md_theme_dark_onTertiaryContainer = Color(0xFFDFE8B1)
val Md_theme_dark_error = Color(0xFFFFB4AB)
val Md_theme_dark_errorContainer = Color(0xFF93000A)
val Md_theme_dark_onError = Color(0xFF690005)
val Md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
val Md_theme_dark_background = Color(0xFF201A16)
val Md_theme_dark_onBackground = Color(0xFFECE0DA)
val Md_theme_dark_surface = Color(0xFF201A16)
val Md_theme_dark_onSurface = Color(0xFFECE0DA)
val Md_theme_dark_surfaceVariant = Color(0xFF51443B)
val Md_theme_dark_onSurfaceVariant = Color(0xFFD6C3B6)

// ============================================================
// COLOR SCHEMES
// ============================================================
// A ColorScheme maps all the color roles to actual colors.
// We define one for light mode and one for dark mode.
// ============================================================

private val LightColorScheme = lightColorScheme(
    primary = Md_theme_light_primary,
    onPrimary = Md_theme_light_onPrimary,
    primaryContainer = Md_theme_light_primaryContainer,
    onPrimaryContainer = Md_theme_light_onPrimaryContainer,
    secondary = Md_theme_light_secondary,
    onSecondary = Md_theme_light_onSecondary,
    secondaryContainer = Md_theme_light_secondaryContainer,
    onSecondaryContainer = Md_theme_light_onSecondaryContainer,
    tertiary = Md_theme_light_tertiary,
    onTertiary = Md_theme_light_onTertiary,
    tertiaryContainer = Md_theme_light_tertiaryContainer,
    onTertiaryContainer = Md_theme_light_onTertiaryContainer,
    error = Md_theme_light_error,
    errorContainer = Md_theme_light_errorContainer,
    onError = Md_theme_light_onError,
    onErrorContainer = Md_theme_light_onErrorContainer,
    background = Md_theme_light_background,
    onBackground = Md_theme_light_onBackground,
    surface = Md_theme_light_surface,
    onSurface = Md_theme_light_onSurface,
    surfaceVariant = Md_theme_light_surfaceVariant,
    onSurfaceVariant = Md_theme_light_onSurfaceVariant,
)

private val DarkColorScheme = darkColorScheme(
    primary = Md_theme_dark_primary,
    onPrimary = Md_theme_dark_onPrimary,
    primaryContainer = Md_theme_dark_primaryContainer,
    onPrimaryContainer = Md_theme_dark_onPrimaryContainer,
    secondary = Md_theme_dark_secondary,
    onSecondary = Md_theme_dark_onSecondary,
    secondaryContainer = Md_theme_dark_secondaryContainer,
    onSecondaryContainer = Md_theme_dark_onSecondaryContainer,
    tertiary = Md_theme_dark_tertiary,
    onTertiary = Md_theme_dark_onTertiary,
    tertiaryContainer = Md_theme_dark_tertiaryContainer,
    onTertiaryContainer = Md_theme_dark_onTertiaryContainer,
    error = Md_theme_dark_error,
    errorContainer = Md_theme_dark_errorContainer,
    onError = Md_theme_dark_onError,
    onErrorContainer = Md_theme_dark_onErrorContainer,
    background = Md_theme_dark_background,
    onBackground = Md_theme_dark_onBackground,
    surface = Md_theme_dark_surface,
    onSurface = Md_theme_dark_onSurface,
    surfaceVariant = Md_theme_dark_surfaceVariant,
    onSurfaceVariant = Md_theme_dark_onSurfaceVariant,
)

// ============================================================
// THEME COMPOSABLE
// ============================================================

/**
 * MemoJarTheme wraps the entire app and provides the color scheme.
 * It automatically uses dark or light colors based on the device setting.
 */
@Composable
fun MemoJarTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Pick the right color scheme based on dark mode setting
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    // Set the status bar color to match the theme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    // Apply the theme to all child composables
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}

