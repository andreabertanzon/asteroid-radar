package com.abcode.asteroidradar.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AsteroidDao {
    @Query("SELECT a.* FROM asteroids AS a ORDER BY a.closeApproachDate")
    fun getAllAsteroids(): Flow<List<AsteroidDto>>

    @Query("SELECT a.* FROM asteroids as a WHERE a.closeApproachDate BETWEEN datetime('now') AND datetime('now', '+7 day') ORDER BY a.closeApproachDate")
    fun getWeeklyAsteroids(): Flow<List<AsteroidDto>>

    @Query("SELECT a.* FROM asteroids as a WHERE a.closeApproachDate BETWEEN datetime('now', '-1 day') AND datetime('now') ORDER BY a.closeApproachDate")
    fun getTodayAsteroids(): Flow<List<AsteroidDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAsteroids(asteroids: List<AsteroidDto>)

}