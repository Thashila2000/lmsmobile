package com.example.lmsmobile.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun LmsTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Indigo600,
            secondary = Purple600,
            background = Color.White
        ),
        typography = Typography,
        content = content
    )
}