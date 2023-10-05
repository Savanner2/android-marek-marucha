package com.example.marekmarucha.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Marek(
    val id: Int,
    @DrawableRes val image: Int,
    @StringRes val title: Int,
    @StringRes val description: Int
)