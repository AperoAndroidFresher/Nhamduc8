package com.example.nhamngocduc.util

import androidx.annotation.DrawableRes
import com.example.nhamngocduc.R

enum class DropDownOption(
    val label: String,
    @DrawableRes
    val iconResId: Int
) {
    REMOVE("Remove", R.drawable.remove),
    SHARE("Share", R.drawable.share),
    ADD_TO_PLAYLIST("Add to playlist", R.drawable.ic_add_playlist),
    RENAME("Rename", R.drawable.ic_rename)
}


val playlistDropDownOptions = listOf(
    DropDownOption.REMOVE,
    DropDownOption.SHARE
)

val playlistWholeDropDownOption = listOf(
    DropDownOption.REMOVE,
    DropDownOption.RENAME
)

val libraryDropDownOptions = listOf(
    DropDownOption.ADD_TO_PLAYLIST,
    DropDownOption.SHARE
)
