package com.example.core.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
        primary = DarkBlue,
        secondary = DarkBrown,
        tertiary = Golden,
        secondaryContainer = LightPurple
)

private val LightColorScheme = lightColorScheme(
        primary = DarkBlue,
        secondary = DarkBrown,
        tertiary = Golden,
        secondaryContainer = LightPurple
)

@Composable
fun ZimranGithubTheme(
        darkTheme: Boolean = isSystemInDarkTheme(),
        content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
    )
}