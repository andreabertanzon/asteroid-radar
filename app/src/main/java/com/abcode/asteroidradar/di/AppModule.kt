package com.abcode.asteroidradar.di

import android.app.Application
import androidx.room.Room
import com.abcode.asteroidradar.Constants
import com.abcode.asteroidradar.api.AsteroidsApi
import com.abcode.asteroidradar.data.AsteroidsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // RETROFIT CLIENT
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideAsteroidsApi(retrofit: Retrofit): AsteroidsApi =
        retrofit.create(AsteroidsApi::class.java)

    // ROOM DATABASE
    @Provides
    @Singleton
    fun provideDatabase(app: Application): AsteroidsDatabase =
        Room.databaseBuilder(app, AsteroidsDatabase::class.java, "asteroids_database")
            .build()
}