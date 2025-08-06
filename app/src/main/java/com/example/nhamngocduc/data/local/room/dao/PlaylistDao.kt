package com.example.nhamngocduc.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.nhamngocduc.data.local.model.entity.PlaylistEntity
import com.example.nhamngocduc.data.local.model.entity.relation.PlaylistWithMusics
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {

    @Query("SELECT * FROM playlists WHERE username = :username ORDER BY playlistId DESC")
    fun getAllPlaylists(username: String): Flow<List<PlaylistEntity>>

    @Query("SELECT * FROM playlists WHERE playlistId = :id")
    fun getPlaylist(id: Int): Flow<PlaylistEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(item: PlaylistEntity)

    @Query("DELETE FROM playlists WHERE playlistId = :playlistId")
    suspend fun deletePlaylist(playlistId: Int)

    @Query("UPDATE playlists SET playlistName = :newName WHERE playlistId = :playlistId")
    suspend fun renamePlaylist(playlistId: Int, newName: String)

    @Transaction
    @Query("SELECT * FROM playlists WHERE playlistId = :playlistId")
    fun getPlaylistWithMusics(playlistId: Int): Flow<List<PlaylistWithMusics>>

    @Query("SELECT COUNT(*) FROM playlist_music_cross_ref WHERE playlistId = :playlistId")
    fun getSongCount(playlistId: Long): Flow<Int>
}