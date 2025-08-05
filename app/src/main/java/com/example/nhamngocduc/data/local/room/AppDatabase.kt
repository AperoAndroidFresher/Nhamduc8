package com.example.nhamngocduc.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.nhamngocduc.data.local.room.coverter.UriTypeConverter
import com.example.nhamngocduc.data.local.room.dao.MusicDao
import com.example.nhamngocduc.data.local.room.dao.PlaylistDao
import com.example.nhamngocduc.data.local.room.dao.RelationDao
import com.example.nhamngocduc.data.local.room.dao.UserDao
import com.example.nhamngocduc.data.local.model.entity.MusicEntity
import com.example.nhamngocduc.data.local.model.entity.PlaylistEntity
import com.example.nhamngocduc.data.local.model.entity.relation.PlaylistMusicCrossRef
import com.example.nhamngocduc.data.local.model.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        MusicEntity::class,
        PlaylistEntity::class,
        PlaylistMusicCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(UriTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun musicDao(): MusicDao

    abstract fun playlistDao(): PlaylistDao

    abstract fun relationshipDao(): RelationDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context) : AppDatabase{
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "nhamngocduc-db"
                )
                    .fallbackToDestructiveMigration(true)
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}