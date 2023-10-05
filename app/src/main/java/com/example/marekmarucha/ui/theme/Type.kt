package com.example.marekmarucha.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.marekmarucha.R

val Chivo = FontFamily(
    Font(R.font.chivo_thin, FontWeight.Thin),
    Font(R.font.chivo_light, FontWeight.Light),
    Font(R.font.chivo_regular, FontWeight.Normal),
    Font(R.font.chivo_bold, FontWeight.Bold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Chivo,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Chivo,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Chivo,
        fontWeight = FontWeight.Light,
        fontSize = 11.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = Chivo,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    )

)