package com.example.nhamngocduc.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nhamngocduc.data.local.model.entity.relation.PlaylistMusicCrossRef

@Dao
interface RelationDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE )
    suspend fun insertPlaylistMusicCrossRef(crossRef: PlaylistMusicCrossRef)

    @Query("SELECT * FROM playlist_music_cross_ref WHERE playlistId = :playlistId AND musicId = :musicId")
    suspend fun getPlaylistSongCrossRef(playlistId: Int, musicId: Long): PlaylistMusicCrossRef?

    @Query("DELETE FROM playlist_music_cross_ref WHERE playlistId = :playlistId AND musicId = :musicId")
    suspend fun deletePlaylistMusicCrossRef(playlistId: Int, musicId: Long)
}