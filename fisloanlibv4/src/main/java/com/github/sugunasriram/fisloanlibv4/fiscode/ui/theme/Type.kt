package com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.github.sugunasriram.fisloanlibv4.R

val robot = FontFamily(
    Font(R.font.robotocondensed_bold, FontWeight.Bold),
    Font(R.font.robotocondensed_regular, FontWeight.Normal),
    Font(R.font.robotocondensed_semibold, FontWeight.SemiBold)
)

val robotTheme = Typography(
    h1 = TextStyle(
        fontFamily = robot,
        fontWeight = FontWeight.Bold,
        fontSize = 96.sp,
        lineHeight = 96.sp,
        letterSpacing = 0.31.sp
    ),
    h3 = TextStyle(
        fontFamily = robot,
        fontWeight = FontWeight.SemiBold,
        fontSize = 30.sp,
        lineHeight = 30.sp,
        letterSpacing = 0.75.sp
    ),

    h5 = TextStyle(
        fontFamily = robot,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    h6 = TextStyle(
        fontFamily = robot,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.009.sp
    ),
    body1 = TextStyle(
        fontFamily = robot,
        fontWeight = FontWeight.W300,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.031.sp
    ),
    body2 = TextStyle(
        fontFamily = robot,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.016.sp
    ),
    button = TextStyle(
        fontFamily = robot,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.031.sp
    ),
    caption = TextStyle(
        fontFamily = robot,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 12.sp,
        letterSpacing = 0.025.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = robot,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 12.sp
    )

)
