package com.abcode.asteroidradar.di

import android.app.Application
import androidx.room.Room
import com.abcode.asteroidradar.Constants
import com.abcode.asteroidradar.api.AsteroidsApi
import com.abcode.asteroidradar.data.AsteroidsDatabase
import com.abcode.asteroidradar.repository.LocalInfoRepository
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()
                )
            )
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