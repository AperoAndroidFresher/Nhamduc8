package com.example.nhamngocduc.util

import androidx.annotation.DrawableRes
import com.example.nhamngocduc.R

enum class DropDownOption(
    val label: String,
    @DrawableRes
    val iconResId: Int
) {
    REMOVE("Remove", R.drawable.remove),
    SHARE("Share", R.drawable.share)
}


val playlistDropDownOption = listOf(
    DropDownOption.REMOVE,
    DropDownOption.SHARE
)

