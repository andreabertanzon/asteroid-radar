package com.abcode.asteroidradar.repository

import com.abcode.asteroidradar.Asteroid
import com.abcode.asteroidradar.api.AsteroidsApi
import com.abcode.asteroidradar.data.AsteroidsDatabase
import com.abcode.asteroidradar.data.asDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class AsteroidRepository @Inject constructor(
    private val api: AsteroidsApi,
    private val db: AsteroidsDatabase
) {

    fun getAsteroids(filter: FilterEnum = FilterEnum.ALL): Flow<List<Asteroid>> {
        return when (filter) {
            FilterEnum.ALL -> {
                db.asteroidDao().getAllAsteroids().map {
                    it.asDomainModel()
                }
            }
            FilterEnum.WEEK -> {
                db.asteroidDao().getWeeklyAsteroids().map {
                    it.asDomainModel()
                }
            }
            else -> {
                db.asteroidDao().getTodayAsteroids().map {
                    it.asDomainModel()
                }
            }
        }
    }
}

