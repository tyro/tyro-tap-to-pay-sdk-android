package com.tyro.taptopay.sdk.demo.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private object Colors {
  val tyroDemoBlue = Color(0xFF0057CD)
  val tyroDemoMidnightBlue = Color(0xFF0A0524)
  val tyroDemoGrey = Color(0xFFE0E0E0)
  val tyroDemoDarkGrey = Color(0xFF899294)
  val tyroDemoBlack = Color(0xFF1C1B1F)
  val tyroDemoRed = Color(0xFFAA1111)
}

private val lightColorScheme =
  lightColorScheme(
    primary = Colors.tyroDemoBlue,
    onPrimary = Color.White,
    secondary = Colors.tyroDemoGrey,
    onSecondary = Colors.tyroDemoBlack,
    tertiary = Colors.tyroDemoMidnightBlue,
    error = Colors.tyroDemoRed,
    onSurface = Colors.tyroDemoBlack,
    surfaceVariant = Colors.tyroDemoGrey,
    onSurfaceVariant = Colors.tyroDemoDarkGrey,
    onBackground = Colors.tyroDemoBlack,
    outline = Colors.tyroDemoBlue,
  )

@Composable
fun SdkDemoTheme(content: @Composable () -> Unit) {
  MaterialTheme(
    colorScheme = lightColorScheme,
    typography =
      Typography(
        bodyLarge =
          TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp,
          ),
        bodyMedium =
          TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
          ),
        bodySmall =
          TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
          ),
      ),
    content = {
      ProvideTextStyle(
        value = TextStyle(color = MaterialTheme.colorScheme.onSurface),
        content = content,
      )
    },
  )
}
