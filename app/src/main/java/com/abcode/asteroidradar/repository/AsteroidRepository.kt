package com.abcode.asteroidradar.repository

import android.util.Log
import com.abcode.asteroidradar.Asteroid
import com.abcode.asteroidradar.BuildConfig
import com.abcode.asteroidradar.api.AsteroidsApi
import com.abcode.asteroidradar.api.getNextSevenDaysFormattedDates
import com.abcode.asteroidradar.api.parseAsteroidsJsonResult
import com.abcode.asteroidradar.data.AsteroidsDatabase
import com.abcode.asteroidradar.data.asDomainModel
import com.abcode.asteroidradar.data.toDtoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.json.JSONObject
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

    suspend fun refreshAsteroids():Boolean {
        var output: Boolean

        withContext(Dispatchers.IO) {
            try {
                val nextSevenDays = getNextSevenDaysFormattedDates()
                val asteroids = api.getAsteroidsAsync(nextSevenDays[0], nextSevenDays[6], BuildConfig.ASTEROID_API_KEY)
                val asteroidsToInsert = parseAsteroidsJsonResult(JSONObject(asteroids))
                db.asteroidDao().insertAsteroids(asteroidsToInsert.toDtoModel())
                output = true
            } catch (err: Throwable) {
                Log.e(
                    "AsteroidRepository",
                    err.localizedMessage ?: "Error retrieving data from API"
                )
                 output = false
            }
        }
        return output
    }
}

