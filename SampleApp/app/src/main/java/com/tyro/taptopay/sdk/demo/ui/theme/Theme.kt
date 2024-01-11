package com.tyro.taptopay.sdk.demo.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.tyro.taptopay.sdk.demo.R

private val lightColorScheme =
    lightColorScheme(
        primary = tyroDemoBlue,
        secondary = tyroDemoGrey,
        tertiary = tyroDemoMidnightBlue,
    )

val DefaultFontFamily = FontFamily(Font(R.font.noto_sans_regular))

@Composable
fun SdkDemoTheme(content: @Composable () -> Unit) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = tyroDemoBlack)
    MaterialTheme(
        colorScheme = lightColorScheme,
        typography =
            Typography(
                bodyLarge =
                    TextStyle(
                        fontFamily = DefaultFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 24.sp,
                    ),
                bodyMedium =
                    TextStyle(
                        fontFamily = DefaultFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                    ),
                bodySmall =
                    TextStyle(
                        fontFamily = DefaultFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                    ),
            ),
        content = {
            ProvideTextStyle(
                value = TextStyle(color = tyroDemoBlack),
                content = content,
            )
        },
    )
}
