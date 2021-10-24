package com.abcode.asteroidradar.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AsteroidDto::class, LocalDto::class], version = 1)
abstract class AsteroidsDatabase : RoomDatabase() {
    abstract fun asteroidDao(): AsteroidDao
    abstract fun localInfoDao(): LocalDao
}