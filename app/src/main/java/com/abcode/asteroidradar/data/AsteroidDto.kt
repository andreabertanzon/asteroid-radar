package com.abcode.asteroidradar.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abcode.asteroidradar.Asteroid

@Entity(tableName="asteroids")
data class AsteroidDto(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
)
