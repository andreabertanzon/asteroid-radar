package com.abcode.asteroidradar.di

import com.abcode.asteroidradar.data.AsteroidsDatabase
import com.abcode.asteroidradar.repository.AsteroidRepository
import com.abcode.asteroidradar.repository.LocalInfoRepository
import javax.inject.Inject

class RefreshDataDependencies @Inject constructor(
    val database: AsteroidsDatabase
) {

}
