package com.abcode.asteroidradar.api

import com.abcode.asteroidradar.PictureOfDay
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface AsteroidsApi {

    @GET("planetary/apod")
    suspend fun getPictureOfDayAsync(@Query("api_key") apiKey: String): PictureOfDay

    @GET("neo/rest/v1/feed")
    suspend fun getAsteroidsAsync(
        @Query("api_key") apiKey: String
    ) : ResponseBody
}