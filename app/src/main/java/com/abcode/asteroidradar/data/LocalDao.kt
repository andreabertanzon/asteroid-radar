package com.abcode.asteroidradar.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LocalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocalInfo(info: LocalDto)

    @Query("SELECT * FROM localInfo WHERE date(creationDate, 'localtime') == date('now', 'localtime')")
    suspend fun getLocalInfo(): LocalDto?
}