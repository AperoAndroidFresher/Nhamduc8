package com.example.nhamngocduc.data.local.model.entity.relation

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Junction
import androidx.room.Relation
import com.example.nhamngocduc.data.local.model.entity.MusicEntity
import com.example.nhamngocduc.data.local.model.entity.PlaylistEntity

@Entity(
    tableName = "playlist_music_cross_ref",
    primaryKeys = ["playlistId", "musicId"],
    foreignKeys = [
        ForeignKey(
            entity = PlaylistEntity::class,
            parentColumns = ["playlistId"],
            childColumns = ["playlistId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = MusicEntity::class,
            parentColumns = ["musicId"],
            childColumns = ["musicId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PlaylistMusicCrossRef(
    val playlistId: Int,
    val musicId: Long
)

data class PlaylistWithMusics(
    @Embedded val playlist: PlaylistEntity,
    @Relation(
        parentColumn = "playlistId",
        entityColumn = "musicId",
        associateBy = Junction(PlaylistMusicCrossRef::class)
    )
    val musics: List<MusicEntity>
)

data class MusicWithPlaylists(
    @Embedded val music: MusicEntity,
    @Relation(
        parentColumn = "musicId",
        entityColumn = "playlistId",
        associateBy = Junction(PlaylistMusicCrossRef::class)
    )
    val playlists: List<PlaylistEntity>
)