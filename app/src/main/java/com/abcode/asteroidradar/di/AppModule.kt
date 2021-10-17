package com.abcode.asteroidradar.di

import com.abcode.asteroidradar.Constants
import com.abcode.asteroidradar.api.AsteroidsApi
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
    fun provideRestaurantApi(retrofit: Retrofit):AsteroidsApi=retrofit.create(AsteroidsApi::class.java)

    // ROOM DATABASE

}