package com.example.nhamngocduc.util

import androidx.compose.runtime.mutableStateListOf
import com.example.nhamngocduc.data.model.Song

object SongList {
    val songsList: MutableList<Song> = mutableStateListOf(
        Song(
            id = 0,
            songName = "Za Rodinu Za Rodinu Za Rodinu Za Rodinu Za Rodinu",
            artistName = "Stalin",
            songDuration = 40000
        ),
        Song(
            id = 1,
            songName = "Za Rodinu",
            artistName = "Stalin",
            songDuration = 40000
        ),
        Song(
            id = 2,
            songName = "Za Rodinu",
            artistName = "Stalin",
            songDuration = 40000
        ),
        Song(
            id = 3,
            songName = "Za Rodinu",
            artistName = "Stalin",
            songDuration = 40000
        ),
        Song(
            id = 4,
            songName = "Za Rodinu",
            artistName = "Stalin",
            songDuration = 40000
        ),
        Song(
            id = 5,
            songName = "Za Rodinu",
            artistName = "Stalin",
            songDuration = 40000
        ),
        Song(
            id = 6,
            songName = "Za Rodinu",
            artistName = "Stalin",
            songDuration = 40000
        ),
        Song(
            id = 7,
            songName = "Za Rodinu",
            artistName = "Stalin",
            songDuration = 40000
        ),
        Song(
            id = 8,
            songName = "Za Rodinu",
            artistName = "Stalin",
            songDuration = 40000
        ),
        Song(
            id = 9,
            songName = "Za Rodinu",
            artistName = "Stalin",
            songDuration = 40000
        ),
    )

    fun removeItem(id: Int) {
        songsList.removeAll { it.id == id }
    }
}