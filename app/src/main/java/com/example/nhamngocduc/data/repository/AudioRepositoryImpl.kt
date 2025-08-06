package com.example.nhamngocduc.data.repository

import android.content.ContentResolver
import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.example.nhamngocduc.domain.model.Song
import com.example.nhamngocduc.domain.repository.AudioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AudioRepositoryImpl(
    private val contentResolver: ContentResolver
) : AudioRepository {
    override suspend fun getAudioFiles(): List<Song> = withContext(Dispatchers.IO) {
        val songs = mutableListOf<Song>()
        val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = "${ MediaStore.Audio.Media.DURATION } > 60000"
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION
        )
        val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"

        val cursor = contentResolver.query(
            uri,
            projection,
            selection,
            null,
            sortOrder
        )
        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)

            while (it.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val title = cursor.getString(titleColumn)
                val artist = cursor.getString(artistColumn)
                val duration = cursor.getLong(durationColumn)

                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                songs.add(
                    Song(
                        localStoreId = id,
                        title = title,
                        artist = artist,
                        duration = duration,
                        contentUri = contentUri
                    )
                )
                Log.d("Song Add", "$title")
            }
        }

        return@withContext songs
    }

}