package com.example.nhamngocduc.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.nhamngocduc.data.local.model.entity.MusicEntity
import com.example.nhamngocduc.data.local.model.entity.relation.MusicWithPlaylists
import kotlinx.coroutines.flow.Flow

@Dao
interface MusicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(music: MusicEntity): Long

    @Query("SELECT * " +
            "FROM musics " +
            "ORDER BY musicId DESC")
    fun getAllMusics(): Flow<List<MusicEntity>>

    @Transaction
    @Query("SELECT * " +
            "FROM musics " +
            "WHERE musicId = :musicId")
    fun getMusicWithPlaylists(musicId: Long): Flow<List<MusicWithPlaylists>>

    @Query("SELECT * " +
            "FROM musics " +
            "WHERE musicId = :musicId")
    suspend fun getMusicById(musicId: Long): MusicEntity?
    @Query("SELECT * " +
            "FROM musics " +
            "WHERE localStoreId = :localStoreId " +
            "LIMIT 1")
    suspend fun getMusicByLocalStoreId(localStoreId: Long): MusicEntity?

    @Query("SELECT * " +
            "FROM musics " +
            "WHERE remoteSourceId = :remoteSourceId " +
            "LIMIT 1")
    suspend fun getMusicByRemoteSourceId(remoteSourceId: String): MusicEntity?

    @Query("SELECT * " +
            "FROM musics " +
            "WHERE remoteSourceId IS NOT NULL ")
    suspend fun getAllRemoteMusics(): List<MusicEntity>

}