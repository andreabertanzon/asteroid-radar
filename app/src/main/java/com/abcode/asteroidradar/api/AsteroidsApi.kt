package com.abcode.asteroidradar.api

import com.abcode.asteroidradar.Asteroid
import com.abcode.asteroidradar.PictureOfDay
import com.abcode.asteroidradar.data.AsteroidDto
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.http.GET
import retrofit2.http.Query

interface AsteroidsApi {

    @GET("planetary/apod")
    suspend fun getPictureOfDayAsync(@Query("api_key") apiKey: String): PictureOfDay

    @GET("neo/rest/v1/feed")
    suspend fun getAsteroidsAsync(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String
    ):String
}