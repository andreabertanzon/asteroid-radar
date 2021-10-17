package com.abcode.asteroidradar.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AsteroidDao {
    @Query("SELECT a.* FROM asteroids AS a ORDER BY a.closeApproachDate")
    fun getAllAsteroids(): Flow<List<AsteroidDto>>

    @Query("SELECT a.* FROM asteroids as a WHERE a.closeApproachDate BETWEEN datetime('now', '-1 week') AND datetime('now') ORDER BY a.closeApproachDate")
    fun getWeeklyAsteroids(): Flow<List<AsteroidDto>>

    @Query("SELECT a.* FROM asteroids as a WHERE a.closeApproachDate BETWEEN datetime('now', '-1 day') AND datetime('now') ORDER BY a.closeApproachDate")
    fun getTodayAsteroids(): Flow<List<AsteroidDto>>
}