package com.example.nhamngocduc.util

import androidx.annotation.DrawableRes
import com.example.nhamngocduc.R
import com.example.nhamngocduc.data.model.Song

//enum class DropDownOption(
//    val label: String,
//    @DrawableRes
//    val iconResId: Int
//) {
//    REMOVE("Remove", R.drawable.remove),
//    SHARE("Share", R.drawable.share)
//}

interface DropDownOption {
    val label: String
    @get:DrawableRes
    val iconResId: Int

    fun execute(song: Song)
}

class RemoveOption(
    override val label: String = "Remove",
    override val iconResId: Int = R.drawable.remove
) : DropDownOption {
    override fun execute(song: Song) {
        SongList.removeItem(song.id)
    }
}

class ShareOption(
    override val label: String = "Share",
    override val iconResId: Int = R.drawable.share
) : DropDownOption {
    override fun execute(song: Song) {
        //TODO:
    }
}
