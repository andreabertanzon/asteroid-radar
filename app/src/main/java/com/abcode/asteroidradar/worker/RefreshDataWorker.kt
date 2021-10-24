package com.abcode.asteroidradar.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.abcode.asteroidradar.repository.AsteroidRepository
import com.abcode.asteroidradar.repository.LocalInfoRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class RefreshDataWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val localInfoRepository: LocalInfoRepository,
    private val asteroidRepository: AsteroidRepository
) : CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        return try {
            withContext(Dispatchers.IO) {
                localInfoRepository.upsertLocalInfo()
                asteroidRepository.refreshAsteroids()
                Result.success()
            }
        } catch (err: Throwable) {
            Log.i("WORKER", err.localizedMessage ?: "Error in worker")
            Result.retry()
        }
    }
}