package com.abcode.asteroidradar.repository

import android.util.Log
import com.abcode.asteroidradar.Asteroid
import com.abcode.asteroidradar.BuildConfig
import com.abcode.asteroidradar.PictureOfDay
import com.abcode.asteroidradar.api.AsteroidsApi
import com.abcode.asteroidradar.api.getNextSevenDaysFormattedDates
import com.abcode.asteroidradar.api.parseAsteroidsJsonResult
import com.abcode.asteroidradar.data.AsteroidsDatabase
import com.abcode.asteroidradar.data.LocalDto
import com.abcode.asteroidradar.data.asDomainModel
import com.abcode.asteroidradar.data.toDtoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class LocalInfoRepository @Inject constructor(
    private val db: AsteroidsDatabase
) {
    suspend fun getLocalInfo(): Boolean =
        withContext(Dispatchers.IO) {
            db.localInfoDao().getLocalInfo()?.fetched ?: false
    }

    suspend fun upsertLocalInfo() {
        withContext(Dispatchers.IO) {
            val existingInfo = db.localInfoDao().getLocalInfo()

            if (existingInfo == null){
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                val dateWithoutTime: String = sdf.format(Date())

                val todayInfo = LocalDto(
                    id = 0,
                    fetched = true,
                    creationDate = dateWithoutTime
                )

                db.localInfoDao().insertLocalInfo(todayInfo)
            } else {
                existingInfo.fetched = true

                db.localInfoDao().insertLocalInfo(existingInfo)
            }
        }
    }

}

