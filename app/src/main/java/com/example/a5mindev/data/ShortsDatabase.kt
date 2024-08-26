package com.towerofapp.a5mindev.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.towerofapp.a5mindev.data.Shorts
import com.towerofapp.a5mindev.data.ShortsDao


@Database(entities = [Shorts::class], version = 1)
abstract class ShortsDatabase : RoomDatabase() {
    abstract fun shortsDao(): ShortsDao

    companion object {
        @Volatile
        private var INSTANCE: ShortsDatabase? = null

        fun getDatabase(context: Context): ShortsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShortsDatabase::class.java,
                    "shorts_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
