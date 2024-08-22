package com.example.a5mindev.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShortsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg shorts: Shorts)

    @Query("DELETE FROM shorts")
    suspend fun deleteAllShorts()

    @Query("SELECT * FROM shorts")
    suspend fun getAllShorts(): List<Shorts>
}