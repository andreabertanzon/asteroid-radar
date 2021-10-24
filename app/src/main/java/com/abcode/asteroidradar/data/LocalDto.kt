package com.abcode.asteroidradar.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName="localInfo")
data class LocalDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var creationDate: String,
    var fetched: Boolean = false
)